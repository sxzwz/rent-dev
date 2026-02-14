package com.rent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.pojo.dto.HouseNewsQueryDTO;
import com.rent.pojo.entity.HouseNews;
import com.rent.pojo.vo.HouseNewsListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 房屋资讯持久化接口
 */
@Mapper
public interface HouseNewsMapper extends BaseMapper<HouseNews> {

    List<HouseNewsListVO> list(HouseNewsQueryDTO houseNewsQueryDTO);

    Integer listCount(HouseNewsQueryDTO houseNewsQueryDTO);

    List<Integer> getIds();
}
