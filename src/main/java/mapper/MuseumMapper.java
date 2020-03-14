package mapper;

import entity.Museum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by teacher ZHANG on 2020/2/19
 */
public interface MuseumMapper {
    public List<Museum> selectAllMuseums(@Param("districtIds") List<Integer> districtIds,
                                         @Param("start") Integer start,
                                         @Param("length") Integer length);
    //根据编号列表查询博物馆信息
    public List<Museum> selectMuseumsByList(@Param("museumIds") List<Integer> museumIds);
    public Integer selectMuseumsCount(@Param("districtIds") List<Integer> districtIds);
    public Museum selectMuseumById(Integer museumId);
}
