package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (UserToRole)表实体类
 *
 * @author JanYork
 * @since 2023-03-26 22:06:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserToRole extends Model<UserToRole> {
    /**
     * 用户与角色中间表
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Integer roleId;
}