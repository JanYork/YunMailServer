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
 * 赞助信息表(Sponsor)表实体类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Sponsor extends Model<Sponsor> {
    /**
     * 赞助表ID
     */
    private Long id;
    /**
     * 赞助用户
     */
    private Long userId;
    /**
     * 赞助留言
     */
    private String sponsorSay;
    /**
     * 赞助金额
     */
    private Double sponsorAmount;
    /**
     * 支付类型
     */
    private Integer payType;
    /**
     * 支付商订单号
     */
    private String tradeNo;
    /**
     * 赞助创建时间
     */
    private Date createTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 支付状态
     */
    private Integer state;
}
