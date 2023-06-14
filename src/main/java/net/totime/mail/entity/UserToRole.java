/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 用户与角色中间表(UserToRole)表实体类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserToRole extends Model<UserToRole> {
    /**
     * 用户与角色中间表ID
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
