package service;

import common.DateUtils;
import entity.Order;
import mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by teacher ZHANG on 2020/2/20
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Integer addOneOrder(Integer museumId, String phoneNum, Date visitDate) {
        Date date = visitDate == null? DateUtils.getNextDate(): visitDate;

        return orderMapper.insertOneOrder(museumId, phoneNum, date);
    }

    @Override
    public Integer getOrdersCount(Integer museumId, String phoneNum, Date visitDate) {
        Date date = visitDate == null? DateUtils.getNextDate(): visitDate;

        return orderMapper.selectOrdersCount(museumId, phoneNum, date);
    }

    @Override
    public List<Map> selectCountsByGroup(List<Integer> museumIds, Date visitDate) {
        return orderMapper.selectCountsByGroup(museumIds, visitDate);
    }

    @Override
    public List<Order> getOrdersByPhone(String phoneNum) {
        return orderMapper.selectOrdersByPhone(phoneNum);
    }

    @Override
    public Integer delAllOrders(List<Integer> orderIds) {
        return orderMapper.deleteAllOrders(orderIds);
    }
}
