/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 云寄用户表(User)表实体类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends Model<User> {
    /**
     * 用户自增ID
     */
    private Long id;
    /**
     * 用户账户
     */
    private String name;
    /**
     * 用户昵称
     */
    private String nickName;
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
     * 用户盐值
     */
    private String salt;
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
}
