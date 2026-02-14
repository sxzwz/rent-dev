package com.rent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.pojo.dto.FlowIndexQueryDTO;
import com.rent.pojo.entity.FlowIndex;
import com.rent.pojo.vo.ScoreVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  流量指标持久化接口
 */
@Mapper
public interface FlowIndexMapper extends BaseMapper<FlowIndex> {

    List<FlowIndex> list(FlowIndexQueryDTO flowIndexQueryDTO);

    Integer listCount(FlowIndexQueryDTO flowIndexQueryDTO);

    List<ScoreVO> getScores(FlowIndexQueryDTO flowIndexQueryDTO);
}
