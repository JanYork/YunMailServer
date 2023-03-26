package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (RoleToPrem)表实体类
 *
 * @author JanYork
 * @since 2023-03-26 22:06:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleToPrem extends Model<RoleToPrem> {
    /**
     * 角色与权限中间表
     */
    private Integer id;
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 权限ID
     */
    private Integer premId;
}