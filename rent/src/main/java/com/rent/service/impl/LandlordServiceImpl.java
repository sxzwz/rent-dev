package com.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rent.context.LocalThreadHolder;
import com.rent.mapper.LandlordMapper;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.LandlordQueryDTO;
import com.rent.pojo.emum.IsAuditEnum;
import com.rent.pojo.entity.Landlord;
import com.rent.pojo.vo.LandlordVO;
import com.rent.service.LandlordService;
import com.rent.utils.AssertUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 房东信息业务逻辑接口实现类
 */
@Service
public class LandlordServiceImpl extends ServiceImpl<LandlordMapper, Landlord> implements LandlordService {

    /**
     * 申请成为房东
     *
     * @param landlord
     * @return
     */
    @Override
    public Result<String> saveEntity(Landlord landlord) {
        // 1.上传身份信息
        AssertUtils.hasText(landlord.getIdcard(), "请输入身份证号");
        AssertUtils.hasText(landlord.getIdcardFront(), "请输入上传身份证正面照");
        AssertUtils.hasText(landlord.getIdcardOpposite(), "请输入上传身份证反面找");
        // 2.校验申请记录(一个人只能有一条房东信息记录)
        LandlordQueryDTO landlordQueryDTO = new LandlordQueryDTO();
        landlordQueryDTO.setUserId(LocalThreadHolder.getUserId());
        Integer count = this.baseMapper.listCount(landlordQueryDTO);
        AssertUtils.isTrue(count == 0, "请勿重新申请");
        // 3.新增申请信息
        landlord.setId(landlordQueryDTO.getId());
        landlord.setCreateTime(LocalDateTime.now());
        landlord.setIsAudit(IsAuditEnum.STATUS_2.getType());
        save(landlord);
        return ApiResult.success("申请成功等待审核");
    }

    /**
     * 修改房东信息
     *
     * @param landlord
     * @return
     */
    @Override
    public Result<String> update(Landlord landlord) {
        updateById(landlord);
        return ApiResult.success("房东信息修改成功");
    }

    /**
     * 查询当前用户房东信息
     *
     * @param landlordQueryDTO
     * @return
     */
    @Override
    public Result<LandlordVO> listUser(LandlordQueryDTO landlordQueryDTO) {
        // 设置当前用户ID
        landlordQueryDTO.setUserId(LocalThreadHolder.getUserId());
        List<LandlordVO> landlordVO = this.baseMapper.list(landlordQueryDTO);
        return ApiResult.success(landlordVO.isEmpty() ? null : landlordVO.get(0));
    }

    /**
     * 查询房东信息列表
     * @param landlordQueryDTO
     * @return
     */
    @Override
    public Result<List<LandlordVO>> list(LandlordQueryDTO landlordQueryDTO) {
        // 查询房东信息列表
        List<LandlordVO> landlordVOS = this.baseMapper.list(landlordQueryDTO);
        // 统计数量
        Integer count = this.baseMapper.listCount(landlordQueryDTO);
        return ApiResult.success(landlordVOS, count);
    }


}
