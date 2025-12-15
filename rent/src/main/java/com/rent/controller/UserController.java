package com.rent.controller;

import com.rent.aop.Pager;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.*;
import com.rent.pojo.entity.User;
import com.rent.pojo.vo.UserVO;
import com.rent.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param userLoginDTO 登录入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public Result<Object> login(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.login(userLoginDTO);
    }


    /**
     * token校验
     */
    @GetMapping(value = "/auth")
    @ResponseBody
    public Result<UserVO> auth() {
        return userService.auth();
    }


    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return Result<UserVO>
     */
    @GetMapping(value = "/getById/{id}")
    @ResponseBody
    public Result<UserVO> getById(@PathVariable Integer id) {
        return userService.getById(id);
    }


    /**
     * 用户注册
     *
     * @param userRegisterDTO 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public Result<String> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.register(userRegisterDTO);
    }

    /**
     * 后台新增用户
     *
     * @param userRegisterDTO 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.save(userRegisterDTO);
    }

    /**
     * 用户信息修改
     *
     * @param userUpdateDTO 修改信息入参
     * @return Result<String> 响应结果
     */

    @PutMapping(value = "/update")
    @ResponseBody
    public Result<UserVO> update(@RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.update(userUpdateDTO);
    }

    /**
     * 后台用户信息修改
     *
     * @param user 信息实体
     * @return Result<String> 响应结果
     */
    @PutMapping(value = "/backUpdate")
    @ResponseBody
    public Result<String> backUpdate(@RequestBody User user) {
        return userService.backUpdate(user);
    }

    /**
     * 用户修改密码
     *
     * @param userUpdatePasswordDTO 修改信息入参
     * @return Result<String> 响应结果
     */
    @PutMapping(value = "/updatePassword")
    @ResponseBody
    public Result<String> updatePassword(@RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO) {
        return userService.updatePassword(userUpdatePasswordDTO);
    }

    /**
     * 通过ID删除用户信息
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Result<String> deleteById(@PathVariable Integer id) {
        return userService.deleteById(id);
    }

    /**
     * 查询用户数据
     *
     * @param userQueryDTO 查询参数
     * @return Result<List < User>> 响应结果
     */
    @Pager
    @PostMapping(value = "/query")
    @ResponseBody
    public Result<List<User>> query(@RequestBody UserQueryDTO userQueryDTO) {
        return userService.query(userQueryDTO);
    }

}

