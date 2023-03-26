package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (Perm)表实体类
 *
 * @author JanYork
 * @since 2023-03-26 17:54:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Perm extends Model<Perm> {
    /**
     * 权限ID
     */
    private Integer id;
    /**
     * 权限路径
     */
    private String permissions;
}