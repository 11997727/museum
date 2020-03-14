package mapper;

import entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by teacher ZHANG on 2020/2/19
 */
public interface OrderMapper {
    public Integer selectOrdersCount(@Param("museumId") Integer museumId,
                                     @Param("phoneNum") String phoneNum,
                                     @Param("visitDate") Date visitDate);

    public List<Map> selectCountsByGroup(@Param("museumIds") List<Integer> museumIds,
                                         @Param("visitDate") Date visitDate);

    public Integer insertOneOrder(@Param("museumId") Integer museumId,
                                  @Param("phoneNum") String phoneNum,
                                  @Param("visitDate") Date visitDate);

    public List<Order> selectOrdersByPhone(String phoneNum);

    public Integer deleteAllOrders(@Param("orderIds") List<Integer> orderIds);
}
