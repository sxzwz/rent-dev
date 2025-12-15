package com.rent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.pojo.dto.LandlordQueryDTO;
import com.rent.pojo.entity.Landlord;
import com.rent.pojo.vo.LandlordVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 房东信息持久化接口
 */

@Mapper
public interface LandlordMapper extends BaseMapper<Landlord> {

    Integer listCount(LandlordQueryDTO landlordQueryDTO);

    List<LandlordVO> list(LandlordQueryDTO landlordQueryDTO);
}
