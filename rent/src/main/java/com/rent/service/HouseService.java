package com.rent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseQueryDTO;
import com.rent.pojo.entity.House;
import com.rent.pojo.vo.*;

import java.util.List;

/**
 * 房屋信息业务逻辑接口
 */
public interface HouseService extends IService<House> {

    // 新增房屋信息
    Result<String> saveEntity(House house);

    // 修改房屋信息
    Result<String> update(House house);

    // 查询房屋信息
    Result<List<HouseListItemVO>> list(HouseQueryDTO houseQueryDTO);

    // 查询房屋类型列表
    Result<List<SelectedVO>> houseTypeList();

    // 查询房屋朝向列表
    Result<List<SelectedVO>> houseDirectionList();

    // 查询房屋大小列表
    Result<List<SelectedVO>> houseSizedList();

    // 查询房屋租金方式列表
    Result<List<SelectedVO>> houseDepositMethodList();

    // 查询房屋是否临近地铁列表
    Result<List<SelectedVO>> houseSubwayList();

    // 查询房屋装修状态
    Result<List<SelectedVO>> houseFitmentStatusList();

    // 查询房屋租赁方式
    Result<List<SelectedVO>> houseRentalTypeList();

    // 查询房屋设施列表
    Result<List<LivingFacilityVO>> houseLivingFacilityList();

    // 查询房东自己的房源信息
    Result<List<HouseListItemVO>> landlordHouseList(HouseQueryDTO houseQueryDTO);

    // 房东上架/下架房源信息
    Result<String> houseStatusDeal(Integer id);

    // 通过ID查询房屋详情信息
    Result<HouseVO> selectById(Integer id);

    // 查询房屋面积条件查询条件范围
    Result<List<SelectedVO>> houseSizeNumber();

    // 查询房屋租金查询条件范围
    Result<List<SelectedVO>> houseRentRange();

    // 统计房屋流量
    Result<List<HouseFlowIndexVO>> listFlowIndex(HouseQueryDTO houseQueryDTO);

    // 流量指标可视化
    Result<List<ChartVO>> listChart(HouseQueryDTO houseQueryDTO);

    // 房屋推荐
    Result<List<HouseListItemVO>> recommend(Integer count);
}
