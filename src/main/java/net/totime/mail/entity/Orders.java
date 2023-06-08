/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.totime.mail.enums.PayType;

import java.io.Serializable;

/**
 * 全局订单表(Orders)表实体类
 *
 * @author JanYork
 * @since 2023-04-14 17:41:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Orders extends Model<Orders> {
    private static final long serialVersionUID = 2469297313211491868L;
    /**
     * 订单ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 订单时间
     */
    private Date date;
    /**
     * 商品ID
     */
    private Integer goodsId;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 支付类型
     */
    private PayType payType;
    /**
     * 支付金额
     */
    private BigDecimal amount;
    /**
     * 支付商订单号
     */
    private String tradeNo;
    /**
     * 支付状态
     */
    private Integer state;
}
