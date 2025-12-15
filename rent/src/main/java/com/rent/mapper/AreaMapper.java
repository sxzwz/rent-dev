package com.rent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.pojo.dto.AreaQueryDTO;
import com.rent.pojo.entity.Area;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 地区持久化层
 */
@Mapper
public interface AreaMapper extends BaseMapper<Area> {

    // 查询地区
    List<Area> list(AreaQueryDTO areaQueryDTO);

    // 统计地区个数
    Integer listCount(AreaQueryDTO areaQueryDTO);
}
