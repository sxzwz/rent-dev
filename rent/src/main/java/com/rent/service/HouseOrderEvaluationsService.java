package com.rent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseOrderEvaluationsQueryDTO;
import com.rent.pojo.entity.HouseOrderEvaluations;
import com.rent.pojo.vo.HouseOrderEvaluationsVO;


import java.util.List;

/**
 * 预约看房评价业务逻辑接口
 */
public interface HouseOrderEvaluationsService extends IService<HouseOrderEvaluations> {


    Result<String> saveEntity(HouseOrderEvaluations houseOrderEvaluations);

    Result<List<HouseOrderEvaluationsVO>> list(HouseOrderEvaluationsQueryDTO houseOrderEvaluationsQueryDTO);

    // 查询房源所产生的预约看房评价信息
    Result<List<HouseOrderEvaluationsVO>> houseList(Integer houseId);
}