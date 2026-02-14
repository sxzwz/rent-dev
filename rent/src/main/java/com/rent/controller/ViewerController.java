package com.rent.controller;


import com.rent.aop.Pager;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseNewsQueryDTO;
import com.rent.pojo.dto.HouseQueryDTO;
import com.rent.pojo.entity.HouseNews;
import com.rent.pojo.vo.HouseListItemVO;
import com.rent.pojo.vo.HouseNewsListVO;
import com.rent.pojo.vo.HouseVO;
import com.rent.pojo.vo.SelectedVO;
import com.rent.service.HouseNewsService;
import com.rent.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 游客控制器
 */
@RestController
@RequestMapping("/viewer")
public class ViewerController {

    @Autowired
    private HouseService houseService;
    @Autowired
    private HouseNewsService houseNewsService;

    /**
     * 查询房屋信息
     *
     * @param houseQueryDTO 查询参数
     * @return Result<List < HouseListItemVO>> 响应结果
     */
    @Pager
    @PostMapping("/listHouse")
    @ResponseBody
    public Result<List<HouseListItemVO>> list(@RequestBody HouseQueryDTO houseQueryDTO) {
        return houseService.list(houseQueryDTO);
    }

    /**
     * 查询房屋户型列表
     *
     * @return Result<List < SelectedVO>> 响应结果
     */
    @GetMapping("/houseSizedList")
    @ResponseBody
    public Result<List<SelectedVO>> houseSizedList() {
        return houseService.houseSizedList();
    }

    /**
     * 查询房屋面积查询条件范围
     *
     * @return Result<List < SelectedVO>> 响应结果
     */
    @GetMapping("/houseSizeNumber")
    @ResponseBody
    public Result<List<SelectedVO>> houseSizeNumber() {
        return houseService.houseSizeNumber();
    }

    /**
     * 查询房屋租金查询条件范围
     *
     * @return Result<List < SelectedVO>> 响应结果
     */
    @GetMapping("/houseRentRange")
    @ResponseBody
    public Result<List<SelectedVO>> houseRentRange() {
        return houseService.houseRentRange();
    }

    /**
     * 通过ID查询房屋详情信息
     *
     * @param id 房源ID
     * @return Result<HouseVO> 响应结果
     */
    @GetMapping("/getHouseById/{id}")
    @ResponseBody
    public Result<HouseVO> selectById(@PathVariable Integer id) {
        return houseService.selectById(id);
    }

    /**
     * 查询房屋资讯信息
     *
     * @param houseNewsQueryDTO 查询参数
     * @return Result<List < FlowIndex>> 响应结果
     */
    @Pager
    @PostMapping("/listHouseNews")
    @ResponseBody
    public Result<List<HouseNewsListVO>> list(@RequestBody HouseNewsQueryDTO houseNewsQueryDTO) {
        return houseNewsService.list(houseNewsQueryDTO);
    }

    /**
     * 通过ID查询资讯详情信息
     */
    @GetMapping("/getNewsById/{id}")
    @ResponseBody
    public Result<HouseNews> getById(@PathVariable Integer id) {
        return houseNewsService.selectById(id);
    }

}