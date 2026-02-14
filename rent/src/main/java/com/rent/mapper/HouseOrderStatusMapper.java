package com.rent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.rent.pojo.dto.HouseOrderStatusQueryDTO;
import com.rent.pojo.entity.HouseOrderStatus;
import com.rent.pojo.vo.HouseOrderStatusVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 预约看房状态流转持久化接口
 */
@Mapper
public interface HouseOrderStatusMapper extends BaseMapper<HouseOrderStatus> {

    List<HouseOrderStatusVO> list(HouseOrderStatusQueryDTO houseOrderStatusQueryDTO);

    Integer listCount(HouseOrderStatusQueryDTO houseOrderStatusQueryDTO);

}