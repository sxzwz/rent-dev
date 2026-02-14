package com.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rent.mapper.HouseOrderStatusMapper;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseOrderStatusQueryDTO;
import com.rent.pojo.emum.HouseOrderStatusEnum;
import com.rent.pojo.entity.HouseOrderStatus;
import com.rent.pojo.vo.HouseOrderStatusVO;
import com.rent.service.HouseOrderStatusService;
import com.rent.utils.AssertUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 预约看房状态流转业务逻辑接口实现类
 */
@Service
public class HouseOrderStatusServiceImpl extends ServiceImpl<HouseOrderStatusMapper, HouseOrderStatus> implements HouseOrderStatusService {




    /**
     * 预约看房评价新增
     *
     * @param houseOrderStatus 实体
     * @return Result<String>
     */
    @Override
    public Result<String> saveEntity(HouseOrderStatus houseOrderStatus) {
        judge(houseOrderStatus);
        houseOrderStatus.setCreateTime(LocalDateTime.now()); // 设置时间
        save(houseOrderStatus);
        return ApiResult.success();
    }

    /**
     * 查询预约看房状态流转
     *
     * @param houseOrderStatusQueryDTO 查询条件
     * @return Result<List < HouseOrderStatusVO>>
     */
    @Override
    public Result<List<HouseOrderStatusVO>> list(HouseOrderStatusQueryDTO houseOrderStatusQueryDTO) {
        List<HouseOrderStatusVO> houseOrderStatusVOS = this.baseMapper.list(houseOrderStatusQueryDTO);
        Integer count = this.baseMapper.listCount(houseOrderStatusQueryDTO);
        statusChange(houseOrderStatusVOS);
        return ApiResult.success(houseOrderStatusVOS, count);
    }

    private void statusChange(List<HouseOrderStatusVO> houseOrderStatusVOS) {
        if (houseOrderStatusVOS != null && !houseOrderStatusVOS.isEmpty()) {
            for (HouseOrderStatusVO houseOrderStatusVO : houseOrderStatusVOS) {
                // 原始状态
                String originText = HouseOrderStatusEnum.getDetail(houseOrderStatusVO.getOriginStatus());
                houseOrderStatusVO.setOldStatusText(originText);
                // 新状态
                String newText = HouseOrderStatusEnum.getDetail(houseOrderStatusVO.getNewId());
                houseOrderStatusVO.setNewStatusText(newText);
            }
        }
    }



    private void judge(HouseOrderStatus houseOrderStatus) {
        AssertUtils.notNull(houseOrderStatus, "实体数据不能为空");
        AssertUtils.notNull(houseOrderStatus.getHouseOrderInfoId(), "预约订单ID不能为空");
        AssertUtils.notNull(houseOrderStatus.getOriginStatus(), "旧状态不能为空");
        AssertUtils.notNull(houseOrderStatus.getNewId(), "新状态不能为空");
    }


}