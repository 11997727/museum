package service;

import entity.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by teacher ZHANG on 2020/2/20
 */
public interface OrderService {
    public Integer addOneOrder(Integer museumId, String phoneNum, Date visitDate);
    public Integer getOrdersCount(Integer museumId, String phoneNum, Date visitDate);
    public List<Map> selectCountsByGroup(List<Integer> museumIds, Date visitDate);
    public List<Order> getOrdersByPhone(String phoneNum);
    public Integer delAllOrders(List<Integer> orderIds);
}
