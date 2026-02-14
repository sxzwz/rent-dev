package com.rent.controller;


import com.rent.aop.Pager;
import com.rent.context.LocalThreadHolder;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.FlowIndexQueryDTO;
import com.rent.pojo.entity.FlowIndex;
import com.rent.pojo.vo.HouseListItemVO;
import com.rent.service.FlowIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 流量指标控制器
 */
@RestController
@RequestMapping("/flow-index")
public class FlowIndexController {

    @Autowired
    private FlowIndexService flowIndexService;

    /**
     * 新增流量指标信息
     *
     * @param flowIndex
     * @return
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody FlowIndex flowIndex) {
        return flowIndexService.saveEntity(flowIndex);
    }

    /**
     * 删除流量指标信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        flowIndexService.removeById(id);
        return ApiResult.success("流量指标删除成功");
    }

    /**
     * 查询流量指标信息
     * @param flowIndexQueryDTO
     * @return
     */
    @Pager
    @PostMapping("/list")
    public Result<List<FlowIndex>> list(@RequestBody FlowIndexQueryDTO flowIndexQueryDTO) {
        return flowIndexService.list(flowIndexQueryDTO);
    }

    /**
     * 浏览操作
     * @param flowIndex
     * @return
     */
    @PostMapping("/viewOperation")
    public Result<String> viewOperation(@RequestBody FlowIndex flowIndex){
        return flowIndexService.viewOperation(flowIndex);
    }

    /**
     * 收藏操作
     * @param flowIndex
     * @return
     */
    @PostMapping("/saveOperation")
    public Result<String> saveOperation(@RequestBody FlowIndex flowIndex){
        return flowIndexService.saveOperation(flowIndex);
    }

    /**
     * 停留操作
     * @param flowIndex
     * @return
     */
    @PostMapping("/stayOperation")
    public Result<String> stayOperation(@RequestBody FlowIndex flowIndex){
        return flowIndexService.stayOperation(flowIndex);
    }

    /**
     * 查询用户名下的各类流量指标信息
     * @param flowIndexQueryDTO
     * @return
     */
    @Pager
    @PostMapping("/listUser")
    public Result<List<FlowIndex>> listUser(@RequestBody FlowIndexQueryDTO flowIndexQueryDTO){
        flowIndexQueryDTO.setUserId(LocalThreadHolder.getUserId());
        return flowIndexService.list(flowIndexQueryDTO);
    }

    /**
     * 查询房屋收藏的房屋数据
     * @param flowIndexQueryDTO
     * @return
     */
    @Pager
    @PostMapping("/saveListHouse")
    public Result<List<HouseListItemVO>> saveListHouse(@RequestBody FlowIndexQueryDTO flowIndexQueryDTO){
        return flowIndexService.saveListHouse(flowIndexQueryDTO);
    }

    /**
     * 查询房屋收藏的房屋咨询数据
     * @param flowIndexQueryDTO
     * @return
     */
    @Pager
    @PostMapping("/saveListHouseNews")
    public Result<List<HouseListItemVO>> saveListHouseNews(@RequestBody FlowIndexQueryDTO flowIndexQueryDTO){
        return flowIndexService.saveListHouseNews(flowIndexQueryDTO);
    }



}
