package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (UserToPrem)表实体类
 *
 * @author JanYork
 * @since 2023-03-26 22:24:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserToPrem extends Model<UserToPrem> {
    /**
     * 用户与权限中间表
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 权限ID
     */
    private Integer premId;
}