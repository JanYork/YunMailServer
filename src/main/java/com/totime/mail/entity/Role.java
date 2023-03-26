package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (Role)表实体类
 *
 * @author JanYork
 * @since 2023-03-26 17:54:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends Model<Role> {
    /**
     * 角色ID
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
}