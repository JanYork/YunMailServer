/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 管理员信息(Admin)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("管理员信息")
@ApiSupport(author = "JanYork")
public class Admin extends Model<Admin> {
    private static final long serialVersionUID = -4603541192641887407L;
    /**
     * 管理员ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "管理员ID", example = "1")
    private Integer id;
    /**
     * 管理员用户
     */
    @ApiModelProperty(value = "管理员用户", example = "admin", required = true)
    private String name;
    /**
     * 管理员密码
     */
    @ApiModelProperty(value = "管理员密码", example = "123456", required = true)
    private String pwd;
    /**
     * 管理员邮箱
     */
    @ApiModelProperty(value = "管理员邮箱", example = "123456@qq.com", required = true)
    private String mail;
    /**
     * 管理员角色
     */
    @TableField("role")
    @ApiModelProperty(value = "管理员角色", example = "admin", required = true)
    private String userRole;
    /**
     * 管理员创建时间
     */
    @ApiModelProperty(value = "管理员创建时间", required = true)
    private Date createTime;
}
