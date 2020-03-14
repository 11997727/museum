package service;

import entity.District;
import mapper.DistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by teacher ZHANG on 2020/2/25
 */
@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getAllDistricts() {
        return districtMapper.selectAllDistricts();
    }
}
