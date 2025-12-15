package com.rent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.AreaQueryDTO;
import com.rent.pojo.dto.UserAreaQueryDTO;
import com.rent.pojo.entity.UserArea;
import com.rent.pojo.vo.UserAreaVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户常居驻地业务逻辑接口
 */
public interface UserAreaService extends IService<UserArea> {

    //新增用户常住地地信息
    Result<String> saveEntity(UserArea userArea);

    //修改增用户常住地信息
    Result<String> update(UserArea userArea);

    //查询用户常居住地信息
    Result<List<UserArea>> list(UserAreaQueryDTO userAreaQueryDTO);

    //查询当前用户常居住地信息
    Result<List<UserAreaVO>> listUser(UserAreaQueryDTO userAreaQueryDTO);


}
