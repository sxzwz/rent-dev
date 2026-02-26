package com.rent.controller;


import com.rent.aop.Pager;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseNewsQueryDTO;
import com.rent.pojo.entity.HouseNews;
import com.rent.pojo.vo.HouseNewsListVO;
import com.rent.service.HouseNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 房屋资讯控制器
 */
@RestController
@RequestMapping("/house-news")
public class HouseNewsController {

    @Autowired
    private HouseNewsService houseNewsService;

    /**
     * 新增房屋资讯信息
     * @param houseNews
     * @return
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody HouseNews houseNews) {
        return houseNewsService.saveEntity(houseNews);
    }


    /**
     * 修改房屋资讯信息
     * @param houseNews
     * @return
     */
    @PutMapping("/update")
    public Result<String> update(@RequestBody HouseNews houseNews) {
        return houseNewsService.updateEntity(houseNews);
    }


    /**
     * 删除房屋资讯信息
     * @param id
     * @return
     */
    @DeleteMapping( "/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        houseNewsService.removeById(id);
        return ApiResult.success("房屋资讯删除成功");
    }

    /**
     * 通过ID查询资讯详情信息
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public Result<HouseNews> getById(@PathVariable Integer id) {
        return houseNewsService.selectById(id);
    }

    /**
     * 查询房屋资讯信息
     *
     * @param houseNewsQueryDTO 查询参数
     * @return Result<List < FlowIndex>> 响应结果
     */
    @Pager
    @PostMapping("/list")
    public Result<List<HouseNewsListVO>> list(@RequestBody HouseNewsQueryDTO houseNewsQueryDTO) {
        return houseNewsService.list(houseNewsQueryDTO);
    }

    /**
     * 房屋资讯推荐
     * @param count
     * @return
     */
    @GetMapping("/recommend/{count}")
    public Result<List<HouseNewsListVO>> recommend(@PathVariable Integer count){
        return houseNewsService.recommend(count);
    }
}
