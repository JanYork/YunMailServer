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
import net.totime.mail.util.BcryptUtil;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 管理员信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "管理员信息", description = "字段与数据库一致")
public class Admin extends Model<Admin> {
    private static final long serialVersionUID = -4597202015906705923L;
    /**
     * 管理员ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "管理员ID", example = "1")
    private Integer id;
    /**
     * 管理员用户
     */
    @ApiModelProperty(value = "管理员用户名称", example = "admin", required = true)
    @NotNull(message = "名称不能为空")
    @Size(min = 3, max = 10, message = "名称长度必须在3-10之间")
    private String name;
    /**
     * 管理员密码
     */
    @ApiModelProperty(value = "管理员密码", example = "123456", required = true)
    @NotNull(message = "密码不能为空")
    private String pwd;
    /**
     * 管理员邮箱
     */
    @ApiModelProperty(value = "管理员邮箱", example = "123456@qq.com", required = true)
    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String mail;
    /**
     * 管理员权限级别
     */
    @ApiModelProperty(value = "管理员权限级别", example = "1", required = true, notes = "详细参考后端返回枚举数据")
    @NotNull(message = "权限不能为空")
    private Integer role;
    /**
     * 管理员创建时间
     */
    @ApiModelProperty(value = "管理员创建时间", example = "2023-06-14 22:59:30")
    private Date createTime;

    /**
     * 密码加密
     *
     * @return {@link Admin}
     */
    public Admin encryptPwd() {
        this.pwd = BcryptUtil.encrypt(this.pwd);
        return this;
    }

    /**
     * 密码比对
     *
     * @return {@link Admin}
     */
    public boolean verifyPwd(String pwd) {
        return BcryptUtil.verify(pwd, this.pwd);
    }
}
