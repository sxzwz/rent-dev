package com.rent.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录参数Dto类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    private String account; // 账号
    private String password; // 密码
}
