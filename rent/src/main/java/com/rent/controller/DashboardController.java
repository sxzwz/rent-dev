package com.rent.controller;


import com.rent.pojo.api.Result;
import com.rent.pojo.vo.ChartVO;
import com.rent.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 仪表盘控制器
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * 统计静态数据
     */
    @GetMapping(value = "/mainStatic")
    public Result<List<ChartVO>> mainStatic() {
        return dashboardService.mainStatic();
    }

    /**
     * 统计房源存量趋势（首页 - 折线图）
     * @param days
     * @return
     */
    @GetMapping("/houseLineChart/{days}")
    public Result<List<ChartVO>> houseLineChart(@PathVariable Integer days) {
        return dashboardService.houseLineChart(days);
    }

    /**
     * 管理员首页 -城市待租房源分布
     * @param limit
     * @return
     */
    @GetMapping("/cityHouseRange/{limit}")
    public Result<List<ChartVO>> cityHouseRange(@PathVariable Integer limit){
        return dashboardService.cituHouseRange(limit);
    }
}