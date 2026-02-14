package com.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rent.context.LocalThreadHolder;
import com.rent.mapper.HouseOrderEvaluationsMapper;
import com.rent.mapper.HouseOrderInfoMapper;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseOrderEvaluationsQueryDTO;
import com.rent.pojo.dto.HouseOrderInfoQueryDTO;
import com.rent.pojo.entity.HouseOrderEvaluations;
import com.rent.pojo.vo.HouseOrderEvaluationsVO;
import com.rent.service.HouseOrderEvaluationsService;
import com.rent.utils.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 预约看房评价业务逻辑接口实现类
 */
@Service
public class HouseOrderEvaluationsServiceImpl extends ServiceImpl<HouseOrderEvaluationsMapper, HouseOrderEvaluations> implements HouseOrderEvaluationsService {


    @Autowired
    private HouseOrderInfoMapper houseOrderInfoMapper;

    /**
     * 预约看房评价新增
     *
     * @param houseOrderEvaluations 实体
     * @return Result<String>
     */
    @Override
    public Result<String> saveEntity(HouseOrderEvaluations houseOrderEvaluations) {
        judge(houseOrderEvaluations);
        // 用户只能针对订单完成一次评论，不能重复评论
        HouseOrderEvaluationsQueryDTO houseOrderEvaluationsQueryDTO = new HouseOrderEvaluationsQueryDTO();
        houseOrderEvaluationsQueryDTO.setUserId(LocalThreadHolder.getUserId());
        houseOrderEvaluations.setHouseOrderInfoId(houseOrderEvaluations.getHouseOrderInfoId());
        Integer count = this.baseMapper.listCount(houseOrderEvaluationsQueryDTO);
        AssertUtils.isFalse(count > 0, "不能重复评论");
        houseOrderEvaluations.setCreateTime(LocalDateTime.now()); // 设置时间
        houseOrderEvaluations.setUserId(LocalThreadHolder.getUserId()); // 设置操作者用户ID
        save(houseOrderEvaluations);
        return ApiResult.success();
    }

    /**
     * 查询预约看房评价列表
     *
     * @param houseOrderEvaluationsQueryDTO 查询条件
     * @return Result<List < HouseOrderEvaluationsVO>>
     */
    @Override
    public Result<List<HouseOrderEvaluationsVO>> list(HouseOrderEvaluationsQueryDTO houseOrderEvaluationsQueryDTO) {
        List<HouseOrderEvaluationsVO> houseOrderEvaluationsVOS = this.baseMapper.list(houseOrderEvaluationsQueryDTO);
        Integer count = this.baseMapper.listCount(houseOrderEvaluationsQueryDTO);
        return ApiResult.success(houseOrderEvaluationsVOS, count);
    }

    /**
     * 查询房源所产生的预约看房评价信息
     *
     * @param houseId 房屋ID
     * @return Result<List < HouseOrderEvaluationsVO>> 响应结果
     */
    @Override
    public Result<List<HouseOrderEvaluationsVO>> houseList(Integer houseId) {
        // 传进来的是房屋ID，通过房屋ID先去查询房屋下面产生的所有的预约看房订单数据
        AssertUtils.notNull(houseId,"房源ID不能为空");
        HouseOrderInfoQueryDTO houseOrderInfoQueryDTO = new HouseOrderInfoQueryDTO();
        houseOrderInfoQueryDTO.setHouseId(houseId);
        List<Integer> ids = houseOrderInfoMapper.getIdsByHouseId(houseId);
        if (ids.isEmpty()) { // 房屋下面连预约看房订单都没有，更谈不上产生的预约看房订单服务评价
            return ApiResult.success(new ArrayList<>());
        }
        // 构造服务评价查询条件类，将查回来的预约看房订单的ID列表设置为查询条件
        HouseOrderEvaluationsQueryDTO houseOrderEvaluationsQueryDTO = new HouseOrderEvaluationsQueryDTO();
        houseOrderEvaluationsQueryDTO.setHouseOrderInfoIds(ids);
        List<HouseOrderEvaluationsVO> houseOrderEvaluationsVOS = this.baseMapper.list(houseOrderEvaluationsQueryDTO);
        return ApiResult.success(houseOrderEvaluationsVOS);
    }

    private void judge(HouseOrderEvaluations houseOrderEvaluations) {
        AssertUtils.notNull(houseOrderEvaluations, "实体数据不能为空");
        AssertUtils.notNull(houseOrderEvaluations.getHouseOrderInfoId(), "预约订单ID不能为空");
        AssertUtils.hasText(houseOrderEvaluations.getText(), "评论内容不能为空");
        AssertUtils.notNull(houseOrderEvaluations.getScore(), "评分不能为空");
    }


}