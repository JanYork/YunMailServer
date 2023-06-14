/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity.back;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 云寄用户表(User)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "用户信息", description = "用户信息表")
@ApiSupport(author = "JanYork")
public class User extends Model<User> {
    private static final long serialVersionUID = 4149134366386885495L;
    /**
     * 用户ID
     */
    @TableId
    @ApiModelProperty(value = "用户ID")
    private Long id;
    /**
     * 用户账户
     */
    @TableField("`name`")
    @ApiModelProperty(value = "用户账户")
    private String name;
    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String avatar;
    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱")
    private String email;
    /**
     * 用户手机
     */
    @ApiModelProperty(value = "用户手机")
    private String phone;
    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码")
    private String pwd;
    /**
     * 是否实名认证
     */
    @ApiModelProperty(value = "是否实名认证")
    private Integer authRealName;
    /**
     * 用户创建时间
     */
    @ApiModelProperty(value = "用户创建时间")
    private Date createTime;
    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态")
    private Integer state;
    /**
     * 用户盐值
     */
    @ApiModelProperty(value = "用户盐值")
    private String salt;
}