package com.rent.controller;


import com.rent.aop.Pager;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.CommunityQueryDTO;
import com.rent.pojo.entity.Community;
import com.rent.pojo.vo.CommunityVO;
import com.rent.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小区控制器
 */
@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    /**
     * 新增小区信息
     *
     * @param community
     * @return
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody Community community) {
        return communityService.saveEntity(community);
    }

    /**
     * 删除小区信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        communityService.removeById(id);
        return ApiResult.success("删除小区信息成功");
    }

    /**
     * 修改小区信息
     * @param community
     * @return
     */
    @PutMapping("/update")
    public Result<String> update(@RequestBody Community community){
        return communityService.update(community);
    }

    /**
     * 查询小区信息
     * @param communityQueryDTO
     * @return
     */
    @Pager
    @PostMapping("/list")
    public Result<List<CommunityVO>> list(@RequestBody CommunityQueryDTO communityQueryDTO){
        return communityService.list(communityQueryDTO);
    }

    /**
     * 根据ID查询小区详情信息
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public Result<CommunityVO> getById(@PathVariable Integer id){
        return communityService.selectById(id);
    }






}
