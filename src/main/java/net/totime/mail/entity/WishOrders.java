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
 * 信件订单表(WishOrders)表实体类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WishOrders extends Model<WishOrders> {
    /**
     * 订单ID
     */
    private Integer id;
    /**
     * 订单编号
     */
    private String ordersSerial;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 订单时间
     */
    private Date date;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 支付类型
     */
    private Integer payType;
    /**
     * 支付金额
     */
    private Double amount;
    /**
     * 支付商订单号
     */
    private String tradeNo;
    /**
     * 支付状态
     */
    private Integer state;
    /**
     * 心愿ID
     */
    private Long wishId;
}
