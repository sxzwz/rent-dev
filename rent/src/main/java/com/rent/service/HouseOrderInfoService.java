package com.rent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseOrderInfoQueryDTO;
import com.rent.pojo.entity.HouseOrderInfo;
import com.rent.pojo.vo.HouseOrderInfoVO;

import java.util.List;

/**
 * 预约看房业务逻辑接口
 */
public interface HouseOrderInfoService  extends IService<HouseOrderInfo> {

    // 保存房屋预约信息
    Result<String> saveEntity(HouseOrderInfo houseOrderInfo);

    // 修改房屋预约信息
    Result<String> updateEntity(HouseOrderInfo houseOrderInfo);

    // 查询房屋预约信息
    Result<List<HouseOrderInfoVO>> list(HouseOrderInfoQueryDTO houseOrderInfoQueryDTO);

    // 查询房东名下维护的房屋所产生的预约看房信息
    Result<List<HouseOrderInfoVO>> listLandlord(HouseOrderInfoQueryDTO houseOrderInfoQueryDTO);
}
