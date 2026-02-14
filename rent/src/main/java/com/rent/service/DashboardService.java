package com.rent.service;



import com.rent.pojo.api.Result;
import com.rent.pojo.vo.ChartVO;

import java.util.List;

/**
 * 仪表盘业务逻辑接口
 */
public interface DashboardService {

    // 统计静态数据
    Result<List<ChartVO>> mainStatic();

    // 统计房源存量趋势（首页 - 折线图）
    Result<List<ChartVO>> houseLineChart(Integer days);

    // 管理员首页 -城市待租房源分布
    Result<List<ChartVO>> cituHouseRange(Integer limit);
}
