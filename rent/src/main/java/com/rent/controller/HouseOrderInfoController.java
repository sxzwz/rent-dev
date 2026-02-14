package com.rent.controller;


import com.rent.aop.Pager;
import com.rent.context.LocalThreadHolder;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseOrderInfoQueryDTO;
import com.rent.pojo.emum.DateTimeSplitEnum;
import com.rent.pojo.entity.HouseOrderInfo;
import com.rent.pojo.vo.HouseOrderInfoVO;
import com.rent.pojo.vo.SelectedVO;
import com.rent.service.HouseOrderInfoService;
import com.rent.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/house-order-info")
public class HouseOrderInfoController {

    @Autowired
    private HouseOrderInfoService houseOrderInfoService;

    /**
     * 保存房屋预约信息
     *
     * @param houseOrderInfo
     * @return
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody HouseOrderInfo houseOrderInfo) {
        return houseOrderInfoService.saveEntity(houseOrderInfo);
    }

    /**
     * 修改房屋预约信息
     *
     * @param houseOrderInfo
     * @return
     */
    @PutMapping("/update")
    public Result<String> update(@RequestBody HouseOrderInfo houseOrderInfo) {
        return houseOrderInfoService.updateEntity(houseOrderInfo);
    }

    /**
     * 删除房屋预约信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        houseOrderInfoService.removeById(id);
        return ApiResult.success("预约看房删除成功");
    }

    /**
     * 生成预约看房时间段
     * @return
     */
    @GetMapping("/split")
    public Result<List<SelectedVO>> split() {
        DateTimeSplitEnum[] dateTimeSplitEnums = DateTimeSplitEnum.values();
        List<SelectedVO> selectedVOS = new ArrayList<>();
        for (DateTimeSplitEnum dateTimeSplitEnum : dateTimeSplitEnums) {
            SelectedVO selectedVO = new SelectedVO(dateTimeSplitEnum.getType(), dateTimeSplitEnum.getDetail());
            selectedVOS.add(selectedVO);
        }
        return ApiResult.success(selectedVOS);
    }

    /**
     * 生成一段时间内的日期
     * @param days
     * @return
     */
    @GetMapping("/{days}")
    public Result<List<String>> generateFutureDates(@PathVariable Integer days){
        return ApiResult.success(DateUtil.generateFutureDates(days));
    }

    /**
     * 查询房屋预约列表
     * @param houseOrderInfoQueryDTO
     * @return
     */
    @Pager
    @PostMapping("/list")
    public Result<List<HouseOrderInfoVO>> list(@RequestBody HouseOrderInfoQueryDTO houseOrderInfoQueryDTO) {
        return houseOrderInfoService.list(houseOrderInfoQueryDTO);
    }

    /**
     * 查询用户自己的预约看房信息
     *
     * @param houseOrderInfoQueryDTO 查询参数
     * @return Result<List < HouseOrderInfoVO>> 响应结果
     */
    @Pager
    @PostMapping("/listUser")
    @ResponseBody
    public Result<List<HouseOrderInfoVO>> listUser(@RequestBody HouseOrderInfoQueryDTO houseOrderInfoQueryDTO) {
        houseOrderInfoQueryDTO.setUserId(LocalThreadHolder.getUserId());
        return houseOrderInfoService.list(houseOrderInfoQueryDTO);
    }

    /**
     * 查询房东名下维护的房屋所产生的预约看房信息
     *
     * @param houseOrderInfoQueryDTO 查询参数
     * @return Result<List < HouseOrderInfoVO>> 响应结果
     */
    @Pager
    @PostMapping("/listLandlord")
    public Result<List<HouseOrderInfoVO>> listLandlord(@RequestBody HouseOrderInfoQueryDTO houseOrderInfoQueryDTO) {
        return houseOrderInfoService.listLandlord(houseOrderInfoQueryDTO);
    }
}
