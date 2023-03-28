package com.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class User extends Model<User> {
    /**
     * 用户自增ID
     */
    private Long id;
    /**
     * 用户昵称
     */
    @TableField("`name`")
    private String name;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户手机
     */
    private String phone;
    /**
     * 用户密码
     */
    private String pwd;
    /**
     * 是否实名认证
     */
    private Integer authRealName;
    /**
     * 用户创建时间
     */
    private Date createTime;
    /**
     * 用户状态
     */
    private Integer state;
    /**
     * 用户盐值
     */
    private String salt;
}