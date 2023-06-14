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
 * 管理员信息表(Admin)表实体类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Admin extends Model<Admin> {
    /**
     * 管理员ID
     */
    private Integer id;
    /**
     * 管理员用户
     */
    private String name;
    /**
     * 管理员密码
     */
    private String pwd;
    /**
     * 管理员邮箱
     */
    private String mail;
    /**
     * 管理员权限级别
     */
    private String role;
    /**
     * 管理员创建时间
     */
    private Date createTime;
}
