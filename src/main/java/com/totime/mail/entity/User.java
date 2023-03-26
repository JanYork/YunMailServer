package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (User)表实体类
 *
 * @author JanYork
 * @since 2023-03-26 17:53:57
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
     * 用户状态
     */
    private Integer state;
}