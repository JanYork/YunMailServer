package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (Admin)表实体类
 *
 * @author JanYork
 * @since 2023-03-26 17:54:04
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
}