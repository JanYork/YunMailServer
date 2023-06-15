/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 云寄用户信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "云寄用户信息", description = "字段与数据库一致")
public class User extends Model<User> {
    private static final long serialVersionUID = -7080358761357607747L;
    /**
     * 用户自增ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户自增ID", example = "1")
    private Long id;
    /**
     * 用户账户
     */
    @ApiModelProperty(value = "用户账号", example = "admin", required = true)
    @NotNull(message = "账号不能为空")
    private String name;
    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", example = "JanYork")
    private String nickName;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像", example = "https://www.baidu.com/img/bd_logo1.png")
    @NotNull(message = "头像不能为空")
    private String avatar;
    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱", example = "123456@qq.com")
    @Email(message = "邮箱格式不正确")
    private String email;
    /**
     * 用户手机
     */
    @ApiModelProperty(value = "用户手机", example = "12345678901")
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", example = "123456", required = true)
    @NotNull(message = "密码不能为空")
    @Size(min = 6, max = 16, message = "密码长度为6-16位")
    private String pwd;
    /**
     * 是否实名认证
     */
    @ApiModelProperty(value = "是否实名认证", example = "1", required = true)
    @NotNull(message = "是否实名?")
    private Boolean authRealName;
    /**
     * 实名信息ID
     */
    @ApiModelProperty(value = "实名信息ID", example = "1")
    private Integer idCardAuthId;
    /**
     * 用户创建时间
     */
    @ApiModelProperty(value = "用户创建时间", example = "2023-06-14 22:59:40")
    private Date createTime;
    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态", example = "1", required = true)
    @NotNull(message = "状态不能为空")
    private Integer state;
}
