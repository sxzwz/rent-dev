package com.rent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.CommunityQueryDTO;
import com.rent.pojo.entity.Community;
import com.rent.pojo.vo.CommunityVO;

import java.util.List;


/**
 * 小区业务接口
 */
public interface CommunityService extends IService<Community> {

    // 新增小区信息
    Result<String> saveEntity(Community community);

    // 修改小区信息
    Result<String> update(Community community);

    // 查询小区信息
    Result<List<CommunityVO>> list(CommunityQueryDTO communityQueryDTO);

    // 根据ID查询小区详情信息
    Result<CommunityVO> selectById(Integer id);
}
