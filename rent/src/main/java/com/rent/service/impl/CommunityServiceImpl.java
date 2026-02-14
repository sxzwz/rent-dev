package com.rent.service.impl;

import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rent.mapper.CommunityMapper;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.CommunityQueryDTO;
import com.rent.pojo.entity.Community;
import com.rent.pojo.vo.CommunityVO;
import com.rent.service.CommunityService;
import com.rent.utils.AssertUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * * 小区业务实现类
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {
    /**
     * 新增小区信息
     *
     * @param community
     * @return
     */
    @Override
    public Result<String> saveEntity(Community community) {
        paramJudge(community);
        community.setCreateTime(LocalDateTime.now());
        save(community);
        return ApiResult.success("小区信息添加成功");
    }

    /**
     * 修改小区信息
     *
     * @param community
     * @return
     */
    @Override
    public Result<String> update(Community community) {
        paramJudge(community);
        updateById(community);
        return ApiResult.success("小区信息修改成功");
    }

    /**
     * 查询小区信息
     *
     * @param communityQueryDTO
     * @return
     */
    @Override
    public Result<List<CommunityVO>> list(CommunityQueryDTO communityQueryDTO) {
        List<CommunityVO> communityVOS = this.baseMapper.list(communityQueryDTO);
        Integer count = this.baseMapper.listCount(communityQueryDTO);
        return ApiResult.success(communityVOS, count);
    }


    /**
     * 通过ID查询小区详情信息
     *
     * @param id 小区ID
     * @return Result<CommunityVO> 响应结果
     */
    @Override
    public Result<CommunityVO> selectById(Integer id) {
        AssertUtils.notNull(id, "小区ID不能为空");
        CommunityQueryDTO communityQueryDTO = new CommunityQueryDTO();
        communityQueryDTO.setId(id);
        List<CommunityVO> communityVOS = this.baseMapper.list(communityQueryDTO);
        return ApiResult.success(communityVOS.isEmpty() ? new CommunityVO() : communityVOS.get(0));
    }

    public void paramJudge(Community community) {
        AssertUtils.hasText(community.getName(), "小区名不能为空");
        AssertUtils.hasText(community.getCover(), "小区封面图不能为空");
        AssertUtils.hasText(community.getCovers(), "小区实况图不能为空");
        AssertUtils.notNull(community.getAreaId(), "小区所属地区不能为空");
        AssertUtils.isTrue(community.getName().length() < 30, "小区名不能超过30个字");

    }
}
