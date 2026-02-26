package com.rent.mapper;

import com.rent.pojo.dto.UserQueryDTO;
import com.rent.pojo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户持久化接口
 */
public interface UserMapper {

    int save(User userInsert);

    List<User> query(UserQueryDTO userQueryDTO);

    int queryCount(UserQueryDTO userQueryDTO);

    int update(User user);

    void batchDelete(@Param(value = "ids") List<Integer> ids);

    User getUserById(@Param(value = "id") Integer id);

    User getUserByAccount(@Param(value = "account") String account);

    User getUserByUsername(@Param(value = "username") String account);

    /**
     * 根据账号、手机号、邮箱同时匹配查询用户（用于找回密码校验）
     */
    User getUserByAccountPhoneEmail(@Param("account") String account,
                                    @Param("phone") String phone,
                                    @Param("email") String email);
}
