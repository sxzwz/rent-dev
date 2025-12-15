package com.rent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.pojo.dto.UserAreaQueryDTO;
import com.rent.pojo.entity.Area;
import com.rent.pojo.entity.UserArea;
import com.rent.pojo.vo.UserAreaVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户常住地持久化接口
 */

@Mapper
public interface UserAreaMapper extends BaseMapper<UserArea> {
    // 查询用户常住地列表
    List<UserArea> list(UserAreaQueryDTO userAreaQueryDTO);
    // 查询用户常住地数量
    Integer listCount(UserAreaQueryDTO userAreaQueryDTO);
    // 查询当前用户常住地列表
    List<UserAreaVO> listUser(UserAreaQueryDTO userAreaQueryDTO);

}
