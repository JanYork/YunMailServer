package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 管理员信息表(Admin)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:32
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
     * 管理员创建时间
     */
    private Date createTime;
}