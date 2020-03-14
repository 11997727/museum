package service;

import entity.Museum;

import java.util.Date;
import java.util.List;

/**
 * Created by teacher ZHANG on 2020/2/19
 */
public interface MuseumService {
    public List<Museum> getAllMuseums(List<Integer> districtIds,
                                      Date visitDate,
                                      Integer start,
                                      Integer length);

    public List<Museum> selectMuseumsByList(List<Integer> museumIds,
                                            Date visitDate);

    public Integer getMuseumsCount(List<Integer> districtIds);

    public Museum getMuseumById(Integer museumId,
                                Date visitDate);
}
