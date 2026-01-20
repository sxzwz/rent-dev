package com.rent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.pojo.dto.CommunityQueryDTO;
import com.rent.pojo.entity.Community;
import com.rent.pojo.vo.CommunityVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper extends BaseMapper<Community> {

    List<CommunityVO> list(CommunityQueryDTO communityQueryDTO);

    Integer listCount(CommunityQueryDTO communityQueryDTO);

}
