/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28 09点36分
 * @description 用户注册信息DTO
 * @since 1.0.0
 */
@Data
@ApiModel(value = "用户注册信息")
public class UserRegisterDTO {
    /**
     * 用户账户
     */
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9\\pP]+$", message = "账户包含非法字符")
    @Size(min = 4, max = 15, message = "昵称长度为4-15个字符")
    @ApiModelProperty(value = "用户账户名称", required = true)
    private String name;
    /**
     * 用户昵称
     */
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5\\pP]+$", message = "昵称包含非法字符")
    @Size(min = 4, max = 15, message = "昵称长度为4-15个字符")
    @ApiModelProperty(value = "用户昵称", required = true)
    private String nickName;
    /**
     * 用户手机
     */
    @NotBlank
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    @ApiModelProperty(value = "用户手机号", required = true)
    private String phone;
    /**
     * 用户密码
     */
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9\\pP]+$", message = "密码包含非法字符")
    @Size(min = 6, max = 16, message = "密码长度为6-16个字符")
    @ApiModelProperty(value = "用户密码", required = true)
    private String pwd;
    /**
     * 验证码
     */
    @NotBlank
    @Size(min = 6, max = 6, message = "验证码长度为6个字符")
    @Pattern(regexp = "^[0-9]+$", message = "验证码只能是数字")
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}
