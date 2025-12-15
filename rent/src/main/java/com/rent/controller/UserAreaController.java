package com.rent.controller;


import com.rent.aop.Pager;
import com.rent.context.LocalThreadHolder;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.UserAreaQueryDTO;
import com.rent.pojo.entity.UserArea;
import com.rent.pojo.vo.UserAreaVO;
import com.rent.service.UserAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户常住地控制器
 */

@RestController
@RequestMapping("/user-area")
@Tag(name = "用户长居驻地管理")
public class UserAreaController {

    @Autowired
    private UserAreaService userAreaService;

    /**
     * 新增用户长居驻地信息
     *
     * @param userArea 信息实体
     * @return
     */
    @PostMapping("/save")
    @Operation(summary = "新增用户长居驻地信息")
    public Result<String> save(@RequestBody UserArea userArea) {
        return userAreaService.saveEntity(userArea);
    }

    /**
     * 修改用户常住地信息
     * @param userArea
     * @return
     */
    @PutMapping("/update")
    public Result<String> update(@RequestBody UserArea userArea) {
        return userAreaService.update(userArea);
    }

    /**
     * 查询当前用户常居住地信息
     *
     * @param userAreaQueryDTO 查询参数
     * @return Result<List < UserArea>> 响应结果
     */
    @Pager
    @PostMapping("/listUser")
    @ResponseBody
    public Result<List<UserAreaVO>> listUser(@RequestBody UserAreaQueryDTO userAreaQueryDTO) {
        // 1.获取当前用户id
        userAreaQueryDTO.setUserId(LocalThreadHolder.getUserId());
        return userAreaService.listUser(userAreaQueryDTO);

    }

    /**
     * 查询用户常居住地信息
     *
     * @param userAreaQueryDTO 查询参数
     * @return Result<List < UserArea>> 响应结果
     */
    @Pager
    @PostMapping("/list")
    @ResponseBody
    public Result<List<UserArea>> list(@RequestBody UserAreaQueryDTO userAreaQueryDTO) {
        return userAreaService.list(userAreaQueryDTO);
    }

}
