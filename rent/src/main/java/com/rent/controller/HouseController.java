package com.rent.controller;

import com.rent.aop.Pager;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseQueryDTO;
import com.rent.pojo.entity.House;
import com.rent.pojo.vo.HouseListItemVO;
import com.rent.pojo.vo.LivingFacilityVO;
import com.rent.pojo.vo.SelectedVO;
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
    @PutMapping("/list")
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



}
