package com.rent.controller;

import com.rent.aop.Pager;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseQueryDTO;
import com.rent.pojo.emum.HouseStatusEnum;
import com.rent.pojo.entity.House;
import com.rent.pojo.vo.*;
import com.rent.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 房屋信息控制器
 */
@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    /**
     * 新增房屋信息
     *
     * @param house
     * @return
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody House house) {
        return houseService.saveEntity(house);
    }

    /**
     * 删除房屋信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        houseService.removeById(id);
        return ApiResult.success("房屋删除成功");
    }

    /**
     * 修改房屋信息
     *
     * @param house
     * @return
     */
    @PutMapping("/update")
    public Result<String> update(@RequestBody House house) {
        return houseService.update(house);
    }

    /**
     * 查询房屋信息
     *
     * @param houseQueryDTO
     * @return
     */
    @PostMapping("/list")
    @Pager
    public Result<List<HouseListItemVO>> list(@RequestBody HouseQueryDTO houseQueryDTO) {
        return houseService.list(houseQueryDTO);
    }

    /**
     * 查询房屋类型列表
     * @return
     */
    @GetMapping("/houseTypeList")
    public Result<List<SelectedVO>> houseTypeList() {
        return houseService.houseTypeList();
    }

    /**
     * 查询房屋朝向列表
     * @return
     */
    @GetMapping("/houseDirectionList")
    public Result<List<SelectedVO>> houseDirectionList() {
        return houseService.houseDirectionList();
    }

    /**
     * 查询房屋大小列表
     * @return
     */
    @GetMapping("/houseSizedList")
    public Result<List<SelectedVO>> houseSizedList() {
        return houseService.houseSizedList();
    }

    /**
     * 查询房屋租金方式列表
     * @return
     */
    @GetMapping("/houseDepositMethodList")
    public Result<List<SelectedVO>> houseDepositMethodList() {
        return houseService.houseDepositMethodList();
    }

    /**
     * 查询房屋是否临近地铁列表
     * @return
     */
    @GetMapping("/houseSubwayList")
    public Result<List<SelectedVO>> houseSubwayList() {
        return houseService.houseSubwayList();
    }

    /**
     * 查询房屋装修状态
     * @return
     */
    @GetMapping("/houseFitmentStatusList")
    public Result<List<SelectedVO>> houseFitmentStatusList() {
        return houseService.houseFitmentStatusList();
    }

    /**
     * 查询房屋租赁方式
     * @return
     */
    @GetMapping("/houseRentalTypeList")
    public Result<List<SelectedVO>>houseRentalTypeList() {
        return houseService.houseRentalTypeList();
    }

    /**
     * 查询房屋设施列表
     * @return
     */
    @GetMapping("/houseLivingFacilityList")
    public Result<List<LivingFacilityVO>>houseLivingFacilityList() {
        return houseService.houseLivingFacilityList();
    }

    /**
     * 查询房东自己的房源信息
     * @param houseQueryDTO
     * @return
     */
    @PostMapping("/landlordHouseList")
    @Pager
    public Result<List<HouseListItemVO>> landlordHouseList(@RequestBody HouseQueryDTO houseQueryDTO){
        return houseService.landlordHouseList(houseQueryDTO);
    }

    /**
     * 房东上架/下架房源信息
     * @param id
     * @return
     */
    @PutMapping("/houseStatusDeal/{id}")
    public Result<String> houseStatusDeal(@PathVariable Integer id){
        return houseService.houseStatusDeal(id);
    }

    /**
     * 通过ID查询房屋详情信息
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public Result<HouseVO> selectById(@PathVariable Integer id){
        return houseService.selectById(id);
    }

    /**
     * 用户首页查询房屋信息 - 前提是只能查询上架的房屋信息
     * @param houseQueryDTO
     * @return
     */
    @Pager
    @PostMapping("/listUser")
    public Result<List<HouseListItemVO>> listUser(@RequestBody HouseQueryDTO houseQueryDTO){
        houseQueryDTO.setStatus(HouseStatusEnum.STATUS_1.getType());
        return houseService.list(houseQueryDTO);
    }

    /**
     * 查询房屋面积条件查询条件范围
     * @return
     */
    @GetMapping("/houseSizeNumber")
    public Result<List<SelectedVO>> houseSizeNumber(){
        return houseService.houseSizeNumber();
    }

    /**
     * 查询房屋租金查询条件范围
     * @return
     */
    @GetMapping("/houseRentRange")
    public Result<List<SelectedVO>> houseRentRange(){
        return houseService.houseRentRange();
    }

    /**
     * 统计房屋流量
     * @param houseQueryDTO
     * @return
     */
    @Pager
    @PostMapping("/listFlowIndex")
    public Result<List<HouseFlowIndexVO>> listFlowIndex(@RequestBody HouseQueryDTO houseQueryDTO){
        return houseService.listFlowIndex(houseQueryDTO);
    }

    /**
     * 流量指标可视化
     *
     * @param houseQueryDTO 查询参数
     * @return Result<List < ChartVO>> 响应结果
     */
    @Pager
    @PostMapping(value = "/listChart")
    public Result<List<ChartVO>> listChart(@RequestBody HouseQueryDTO houseQueryDTO) {
        return houseService.listChart(houseQueryDTO);
    }

    /**
     * 房屋推荐
     * @param count
     * @return
     */
    @GetMapping("/recommend/{count}")
    public Result<List<HouseListItemVO>> recommend(@PathVariable Integer count){
        return houseService.recommend(count);
    }



}
