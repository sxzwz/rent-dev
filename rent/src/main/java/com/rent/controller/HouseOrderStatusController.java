package com.rent.controller;


import com.rent.aop.Pager;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseOrderStatusQueryDTO;
import com.rent.pojo.entity.HouseOrderStatus;
import com.rent.pojo.vo.HouseOrderStatusVO;
import com.rent.service.HouseOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 预约看房状态流转评价控制器
 */
@RestController
@RequestMapping("/house-order-status")
public class HouseOrderStatusController {

    @Autowired
    private HouseOrderStatusService houseOrderStatusService;

    @PostMapping("/save")
    public Result<String> list(@RequestBody HouseOrderStatus houseOrderStatus) {
        return houseOrderStatusService.saveEntity(houseOrderStatus);
    }


    /**
     * 查询预约看房状态流转评价信息
     *
     * @param houseOrderStatusQueryDTO 查询参数
     * @return Result<List < HouseOrderStatusVO>> 响应结果
     */
    @Pager
    @PostMapping(value = "/list")
    public Result<List<HouseOrderStatusVO>> list(@RequestBody HouseOrderStatusQueryDTO houseOrderStatusQueryDTO) {
        return houseOrderStatusService.list(houseOrderStatusQueryDTO);
    }

}