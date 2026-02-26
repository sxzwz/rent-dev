package com.rent.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 找回密码/重置密码入参：需同时校验账号、手机号、邮箱一致后才允许修改密码
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResetPasswordDTO {
    private String account;   // 账号
    private String phone;     // 手机号
    private String email;     // 邮箱
    private String newPassword;   // 新密码（前端传入已二次 MD5 加密后的值）
    private String againPassword; // 再次输入新密码（前端传入已二次 MD5 加密后的值）
}