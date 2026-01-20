package com.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rent.context.LocalThreadHolder;
import com.rent.mapper.HouseMapper;
import com.rent.mapper.LandlordMapper;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseQueryDTO;
import com.rent.pojo.dto.LandlordQueryDTO;
import com.rent.pojo.emum.*;
import com.rent.pojo.entity.House;
import com.rent.pojo.vo.HouseListItemVO;
import com.rent.pojo.vo.LandlordVO;
import com.rent.pojo.vo.LivingFacilityVO;
import com.rent.pojo.vo.SelectedVO;
import com.rent.service.HouseService;
import com.rent.utils.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 房屋信息业务逻辑接口实现类
 */
@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

    @Autowired
    private LandlordMapper landlordMapper;

    /**
     * 新增房屋信息
     *
     * @param house
     * @return
     */
    @Override
    public Result<String> saveEntity(House house) {
        // 校验房屋信息
        paramJudge(house);
        // 判断用户角色(只有房东才能添加房屋信息)
        LandlordQueryDTO landlordQueryDTO = new LandlordQueryDTO();
        landlordQueryDTO.setUserId(LocalThreadHolder.getUserId());
        List<LandlordVO> landlordVOS = landlordMapper.list(landlordQueryDTO);
        AssertUtils.isFalse(landlordVOS.isEmpty(), "房东信息异常,非法操作");
        LandlordVO landlordVO = landlordVOS.get(0);
        AssertUtils.isTrue(landlordVO.getIsAudit(), "房东认证信息待审核中，请稍后再试");
        house.setLandlordId(landlordVO.getId());
        house.setCreateTime(LocalDateTime.now());
        save(house);

        return ApiResult.success("房屋信息添加成功");
    }

    /**
     * 修改房屋信息
     *
     * @param house
     * @return
     */
    @Override
    public Result<String> update(House house) {
        // 校验房屋信息
        paramJudge(house);
        // 修复房屋信息
        updateById(house);
        return ApiResult.success("房屋信息修改成功");
    }

    /**
     * 查询房屋信息
     *
     * @param houseQueryDTO
     * @return
     */
    @Override
    public Result<List<HouseListItemVO>> list(HouseQueryDTO houseQueryDTO) {
        List<HouseListItemVO> houseListItemVOS = this.baseMapper.list(houseQueryDTO);
        Integer count = this.baseMapper.listCount(houseQueryDTO);
        for (HouseListItemVO houseListItemVO : houseListItemVOS) {
            // 通过朝向ID,设置房屋朝向信息
            if (Objects.nonNull(houseListItemVO.getDirectionId())) {
                String detail = HouseDirectionEnum.getDetail(houseListItemVO.getDirectionId());
                houseListItemVO.setDirectionName(detail);
            }
            // 通过押金方式ID,设置房屋押金信息
            if (Objects.nonNull(houseListItemVO.getDepositMethodId())) {
                String detail = HouseDepositEnum.getDetail(houseListItemVO.getDepositMethodId());
                houseListItemVO.setDepositMethodName(detail);
            }
            // 通过装修状态ID,设置房屋装修状态信息
            if (Objects.nonNull(houseListItemVO.getFitmentStatusId())) {
                String detail = HouseFitmentEnum.getDetail(houseListItemVO.getFitmentStatusId());
                houseListItemVO.setFitmentStatusName(detail);
            }

        }
        return ApiResult.success(houseListItemVOS, count);
    }

    /**
     * 查询房屋类型列表
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseTypeList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseTypeEnum.values())
                .map(houseTypeEnum -> new SelectedVO(houseTypeEnum.getType(), houseTypeEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    /**
     * 查询房屋朝向列表
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseDirectionList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseDirectionEnum.values())
                .map(houseDirectionEnum -> new SelectedVO(houseDirectionEnum.getType(), houseDirectionEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    /**
     * 查询房屋大小列表
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseSizedList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseSizedEnum.values())
                .map(houseSizedEnum -> new SelectedVO(houseSizedEnum.getType(), houseSizedEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    /**
     * 查询房屋租金方式列表
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseDepositMethodList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseDepositEnum.values())
                .map(houseDepositMethodEnum -> new SelectedVO(houseDepositMethodEnum.getType(), houseDepositMethodEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<SelectedVO>> houseSubwayList() {
        List<SelectedVO> selectedVOS = Arrays.stream(IsSubwayEnum.values())
                .map(isSubwayEnum -> new SelectedVO(isSubwayEnum.getType(), isSubwayEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<SelectedVO>> houseFitmentStatusList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseFitmentEnum.values())
                .map(houseFitmentEnum -> new SelectedVO(houseFitmentEnum.getType(), houseFitmentEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<SelectedVO>> houseRentalTypeList() {
        List<SelectedVO> selectedVOS = Arrays.stream(RentalTypeEnum.values())
                .map(rentalTypeEnum -> new SelectedVO(rentalTypeEnum.getType(), rentalTypeEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<LivingFacilityVO>> houseLivingFacilityList() {
        List<LivingFacilityVO> livingFacilityVOS = Arrays.stream(LivingFacilitiesEnum.values())
                .map(livingFacilitiesEnum -> new LivingFacilityVO(livingFacilitiesEnum.getType(), livingFacilitiesEnum.getSelected()))
                .collect(Collectors.toList());
        return ApiResult.success(livingFacilityVOS);
    }

    /**
     * 校验房屋信息
     *
     * @param house
     */
    private void paramJudge(House house) {
        AssertUtils.hasText(house.getName(), "房屋名称不能为空");
        AssertUtils.hasText(house.getCover(), "房屋封面不能为空");
        AssertUtils.hasText(house.getFloor(), "请填写楼层");
        AssertUtils.hasText(house.getCovers(), "房屋实况图不能为空");
        AssertUtils.notNull(house.getTypeId(), "请设置房屋类型");
        AssertUtils.notNull(house.getSizeNumber(), "请填写房屋产权面积");
        AssertUtils.notNull(house.getDirectionId(), "请设置房屋朝向");
        AssertUtils.notNull(house.getSizedId(), "请设置户型");
        AssertUtils.notNull(house.getRent(), "请设置租金");
        AssertUtils.notNull(house.getDepositMethodId(), "请设置押金方式");
        AssertUtils.notNull(house.getIsSubway(), "请设置是否临近地铁");
        if (house.getIsSubway()) { // 一旦你设置临近地铁，就要补充线路
            AssertUtils.notNull(house.getSubwayLine(), "请设置地铁线路");
        }
        AssertUtils.notNull(house.getFitmentStatusId(), "请设置装修类型");
        AssertUtils.notNull(house.getRentalType(), "请设置租赁方式");
        // 名称长度显示
        AssertUtils.isTrue(house.getName().length() < 30, "房屋名称最多30个字"); // 名称长度校验
        // 实况图上传数量显示
        String covers = house.getCovers();
        long count = covers.chars().filter(c -> c == ',').count();
        AssertUtils.isTrue(count < 6, "实况图最多上传6张");
        // 楼层只能设置为高中低
        AssertUtils.isTrue(house.getFloor().length() <= 1, "楼层只能补充为高或者低、中");
    }
}
