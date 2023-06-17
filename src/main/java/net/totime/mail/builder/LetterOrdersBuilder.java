/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.builder;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 信件订单构建器
 *
 * @author JanYork
 * @since 2023-06-14 22:59:36
 */
@Data
@Builder
public class LetterOrdersBuilder {
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
     * 支付类型
     */
    private Integer payType;
    /**
     * 支付状态
     */
    private Integer state;
    /**
     * 信件ID
     */
    private Long letterId;
}
