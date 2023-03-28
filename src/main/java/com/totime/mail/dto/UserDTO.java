package com.totime.mail.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用户注册DTO
 *
 * @author JanYork
 * @since 2023-03-27 22:13:35
 */
@Data
public class UserDTO {
    /**
     * 用户昵称
     */
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5\\pP]+$", message = "昵称包含非法字符")
    @Size(min = 4, max = 15, message = "昵称长度为4-15个字符")
    private String name;
    /**
     * 用户手机
     */
    @NotBlank
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;
    /**
     * 用户密码
     */
    @NotBlank
    @Size(min = 6, max = 16, message = "密码长度为6-16个字符")
    private String pwd;
    /**
     * 验证码
     */
    @NotBlank
    @Size(min = 6, max = 6, message = "验证码长度为6个字符")
    @Pattern(regexp = "^[0-9]+$", message = "验证码只能是数字")
    private String code;
}