package com.rent.service;

import com.rent.pojo.api.Result;
import com.rent.pojo.dto.*;
import com.rent.pojo.entity.User;
import com.rent.pojo.vo.ChartVO;
import com.rent.pojo.vo.UserVO;

import java.util.List;

/**
 * 用户服务类
 */
public interface UserService {

    Result<String> register(UserRegisterDTO userRegisterDTO);

    Result<Object> login(UserLoginDTO userLoginDTO);

    Result<UserVO> auth();

    Result<List<User>> query(UserQueryDTO userQueryDto);

    Result<UserVO> update(UserUpdateDTO userUpdateDTO);

    Result<String> batchDelete(List<Integer> ids);

    Result<String> updatePassword(UserUpdatePasswordDTO userUpdatePasswordDto);

    Result<UserVO> getById(Integer id);

    Result<String> save(UserRegisterDTO userRegisterDTO);

    Result<String> backUpdate(User user);

    Result<List<ChartVO>> daysQuery(Integer day);

    Result<String> deleteById(Integer id);

    /**
     * 找回密码：校验账号+手机号+邮箱一致后重置密码
     */
    Result<String> resetPassword(UserResetPasswordDTO userResetPasswordDTO);

}
