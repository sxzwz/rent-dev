package com.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rent.context.LocalThreadHolder;
import com.rent.mapper.UserAreaMapper;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.AreaQueryDTO;
import com.rent.pojo.dto.UserAreaQueryDTO;
import com.rent.pojo.entity.Area;
import com.rent.pojo.entity.UserArea;
import com.rent.pojo.vo.UserAreaVO;
import com.rent.service.UserAreaService;
import com.rent.utils.AssertUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户常住地业务逻辑接口实现类
 */

@Service
public class UserAreaServiceImpl extends ServiceImpl<UserAreaMapper, UserArea> implements UserAreaService {

    /**
     * 新增用户常居住地
     *
     * @param userArea 实体信息
     * @return
     */
    @Override
    public Result<String> saveEntity(UserArea userArea) {
        UserAreaQueryDTO userAreaQueryDTO = new UserAreaQueryDTO();
        // 1.获取并设置当前用户id
        userAreaQueryDTO.setUserId(LocalThreadHolder.getUserId());
        // 2.查看当前用户是否已存在常居住地
        Integer count = baseMapper.listCount(userAreaQueryDTO);
        AssertUtils.isTrue(count == 0, "请不要重复新增常居住地");
        // 3.保存常住地信息
        save(userArea);
        return ApiResult.success("用户常住地新增成功");
    }

    /**
     * 修改用户常居住地
     *
     * @param userArea 实体信息
     * @return
     */
    @Override
    public Result<String> update(UserArea userArea) {
        updateById(userArea);
        return ApiResult.success("用户常住地修改成功");
    }

    /**
     * 查询用户常居住地
     *
     * @param userAreaQueryDTO 查询条件
     * @return
     */
    @Override
    public Result<List<UserArea>> list(UserAreaQueryDTO userAreaQueryDTO) {
        //  1.查询用户常住地列表
        List<UserArea> list = this.baseMapper.list(userAreaQueryDTO);
        // 2.查询用户常住地数量
        Integer count = this.baseMapper.listCount(userAreaQueryDTO);

        return ApiResult.success(list, count);

    }

    /**
     * 查询当前用户的常住地信息
     *
     * @param userAreaQueryDTO
     * @return
     */
    @Override
    public Result<List<UserAreaVO>> listUser(UserAreaQueryDTO userAreaQueryDTO) {
        List<UserAreaVO> list = this.baseMapper.listUser(userAreaQueryDTO);
        return ApiResult.success(list);
    }
}
