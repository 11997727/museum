package service;

import common.DateUtils;
import entity.Museum;
import mapper.MuseumMapper;
import mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by teacher ZHANG on 2020/2/19
 */
@Service
public class MuseumServiceImpl implements MuseumService {
    @Autowired
    private MuseumMapper museumMapper;

    @Autowired
    private OrderMapper orderMapper;

    //转换museum当中的休息日：1-休息 0-工作
    private void dayOffConvert(Museum museum, Date visitDate) {
        Date date = visitDate == null? DateUtils.getNextDate(): visitDate;

        if (DateUtils.isDayOff(date, museum.getDayOff())) {
            museum.setDayOff(1); //当天休息
        }
        else {
            museum.setDayOff(0); //当天工作
        }
    }

    //查询博物馆预约数
    private Integer getOrdersCount(Integer museumId, Date visitDate) {
        Date date = visitDate == null? DateUtils.getNextDate(): visitDate;

        return orderMapper.selectOrdersCount(museumId, null, date);
    }

    @Override
    public List<Museum> getAllMuseums(List<Integer> districtIds, Date visitDate, Integer start, Integer length) {
        List<Museum> museums = museumMapper.selectAllMuseums(districtIds, start, length);

        //设置OrderNum
        for (Museum m : museums) {
            m.setOrderNum(getOrdersCount(m.getMuseumId(), visitDate));
            dayOffConvert(m, visitDate);
        }

        return museums;
    }

    @Override
    public List<Museum> selectMuseumsByList(List<Integer> museumIds, Date visitDate) {
        List<Museum> museums = museumMapper.selectMuseumsByList(museumIds);

        for (Museum m : museums) {
            m.setOrderNum(getOrdersCount(m.getMuseumId(), visitDate));
            dayOffConvert(m, visitDate);
        }

        return museums;
    }

    @Override
    public Integer getMuseumsCount(List<Integer> districtIds) {
        return museumMapper.selectMuseumsCount(districtIds);
    }

    @Override
    public Museum getMuseumById(Integer museumId, Date visitDate) {
        Museum museum = museumMapper.selectMuseumById(museumId);

        museum.setOrderNum(getOrdersCount(museum.getMuseumId(), visitDate));
        dayOffConvert(museum, visitDate);

        return museum;
    }



}
