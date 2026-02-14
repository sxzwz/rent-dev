package com.rent.controller;


import com.rent.aop.Pager;
import com.rent.context.LocalThreadHolder;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseOrderEvaluationsQueryDTO;
import com.rent.pojo.entity.HouseOrderEvaluations;
import com.rent.pojo.vo.HouseOrderEvaluationsVO;
import com.rent.service.HouseOrderEvaluationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预约看房评价控制器
 */
@RestController
@RequestMapping("/house-order-evaluations")
public class HouseOrderEvaluationsController {

    @Autowired
    private HouseOrderEvaluationsService houseOrderEvaluationsService;

    /**
     * 新增预约看房评价信息
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody HouseOrderEvaluations houseOrderEvaluations) {
        return houseOrderEvaluationsService.saveEntity(houseOrderEvaluations);
    }

    /**
     * 删除预约看房评价信息
     */
    @DeleteMapping( "/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        houseOrderEvaluationsService.removeById(id);
        return ApiResult.success("预约看房评价删除成功");
    }

    /**
     * 查询预约看房评价信息
     *
     * @param houseOrderEvaluationsQueryDTO 查询参数
     * @return Result<List < HouseOrderEvaluationsVO>> 响应结果
     */
    @Pager
    @PostMapping( "/list")
    public Result<List<HouseOrderEvaluationsVO>> list(@RequestBody HouseOrderEvaluationsQueryDTO houseOrderEvaluationsQueryDTO) {
        return houseOrderEvaluationsService.list(houseOrderEvaluationsQueryDTO);
    }

    /**
     * 查询用户自己的预约看房评价信息
     *
     * @param houseOrderEvaluationsQueryDTO 查询参数
     * @return Result<List < HouseOrderEvaluationsVO>> 响应结果
     */
    @Pager
    @PostMapping(value = "/listUser")
    @ResponseBody
    public Result<List<HouseOrderEvaluationsVO>> listUser(@RequestBody HouseOrderEvaluationsQueryDTO houseOrderEvaluationsQueryDTO) {
        houseOrderEvaluationsQueryDTO.setUserId(LocalThreadHolder.getUserId());
        return houseOrderEvaluationsService.list(houseOrderEvaluationsQueryDTO);
    }


    /**
     * 查询房源所产生的预约看房评价信息
     *
     * @param houseId 房屋ID
     * @return Result<List < HouseOrderEvaluationsVO>> 响应结果
     */
    @Pager
    @GetMapping( "/houseList/{houseId}")
    @ResponseBody
    public Result<List<HouseOrderEvaluationsVO>> houseList(@PathVariable Integer houseId) {
        return houseOrderEvaluationsService.houseList(houseId);
    }


}