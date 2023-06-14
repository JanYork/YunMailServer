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
 * 祝福语录表(BlessingToWish)表实体类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlessingToWish extends Model<BlessingToWish> {
    /**
     * 祝福语ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 祝福语
     */
    private Integer blessing;
    /**
     * 愿望ID
     */
    private Long wishId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否过滤
     */
    private Integer isFilter;
}
