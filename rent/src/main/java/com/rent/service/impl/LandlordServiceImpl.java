package com.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rent.context.LocalThreadHolder;
import com.rent.mapper.LandlordMapper;
import com.rent.mapper.UserMapper;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.LandlordQueryDTO;
import com.rent.pojo.emum.IsAuditEnum;
import com.rent.pojo.emum.RoleEnum;
import com.rent.pojo.entity.Landlord;
import com.rent.pojo.entity.User;
import com.rent.pojo.vo.LandlordVO;
import com.rent.service.LandlordService;
import com.rent.utils.AssertUtils;
import com.rent.utils.IdCardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 房东信息业务逻辑接口实现类
 */
@Service
public class LandlordServiceImpl extends ServiceImpl<LandlordMapper, Landlord> implements LandlordService {

    @Autowired
    private UserMapper userMapper;


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
        // 校验身份证合法性
        AssertUtils.isTrue(IdCardValidator.validate(landlord.getIdcard()), "请输入合法的身份证号");
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
        if (landlord.getIsAudit()) {
            // 管理员才能修改房东信息
            User user = userMapper.getUserById(LocalThreadHolder.getUserId());
            AssertUtils.equals(user.getId(), RoleEnum.ADMIN.getRole(),  "无操作权限");
        }
        updateById(landlord);
        return ApiResult.success("房东信息修改成功");
    }

    /**
     * 查询当前用户自己的申请房东记录
     *
     * @param landlordQueryDTO
     * @return
     */
    @Override
    public Result<LandlordVO> listUser(LandlordQueryDTO landlordQueryDTO) {
        landlordQueryDTO.setUserId(LocalThreadHolder.getUserId()); // 设置上用户ID，数据隔离，经过这样处理，用户只能查看到自己名下的房东申请记录
        List<LandlordVO> landlordVOS = this.baseMapper.list(landlordQueryDTO);
        if (landlordVOS.isEmpty()) {
            return ApiResult.success();
        }
        LandlordVO landlordVO = landlordVOS.get(0);
        String idcard = landlordVO.getIdcard();
        // 身份证号脱敏处理
        String idCardCode = idcard.substring(0, 6) + "****" + idcard.substring(idcard.length() - 2);
        landlordVO.setIdcard(idCardCode);
        return ApiResult.success(landlordVO);
    }

    /**
     * 查询房东信息列表
     *
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
