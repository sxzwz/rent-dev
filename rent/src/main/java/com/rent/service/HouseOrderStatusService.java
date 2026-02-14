package com.rent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseOrderStatusQueryDTO;
import com.rent.pojo.entity.HouseOrderStatus;
import com.rent.pojo.vo.HouseOrderStatusVO;


import java.util.List;

/**
 * 预约看房状态流转业务逻辑接口
 */
public interface HouseOrderStatusService extends IService<HouseOrderStatus> {

    Result<List<HouseOrderStatusVO>> list(HouseOrderStatusQueryDTO houseOrderStatusQueryDTO);

    Result<String> saveEntity(HouseOrderStatus houseOrderStatus);

}