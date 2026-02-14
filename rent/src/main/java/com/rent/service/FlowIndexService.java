package com.rent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.FlowIndexQueryDTO;
import com.rent.pojo.entity.FlowIndex;
import com.rent.pojo.vo.HouseListItemVO;

import java.util.List;

/**
 *  流量指标业务逻辑接口
 */
public interface FlowIndexService extends IService<FlowIndex> {

    // 新增流量指标信息
    Result<String> saveEntity(FlowIndex flowIndex);

    // 查询流量指标信息
    Result<List<FlowIndex>> list(FlowIndexQueryDTO flowIndexQueryDTO);

    // 浏览操作
    Result<String> viewOperation(FlowIndex flowIndex);

    // 收藏操作
    Result<String> saveOperation(FlowIndex flowIndex);

    // 停留操作
    Result<String> stayOperation(FlowIndex flowIndex);

    // 查询房屋收藏的房屋数据
    Result<List<HouseListItemVO>> saveListHouse(FlowIndexQueryDTO flowIndexQueryDTO);

    // 查询房屋收藏的房屋咨询数据
    Result<List<HouseListItemVO>> saveListHouseNews(FlowIndexQueryDTO flowIndexQueryDTO);
}
