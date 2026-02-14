package com.rent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.rent.pojo.dto.HouseOrderEvaluationsQueryDTO;
import com.rent.pojo.entity.HouseOrderEvaluations;
import com.rent.pojo.vo.HouseOrderEvaluationsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 预约看房评价持久化接口
 */
@Mapper
public interface HouseOrderEvaluationsMapper extends BaseMapper<HouseOrderEvaluations> {

    List<HouseOrderEvaluationsVO> list(HouseOrderEvaluationsQueryDTO houseOrderEvaluationsQueryDTO);

    Integer listCount(HouseOrderEvaluationsQueryDTO houseOrderEvaluationsQueryDTO);

}