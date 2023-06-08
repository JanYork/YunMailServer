/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.enums;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/14
 * @description 支付状态枚举
 * @see Enum
 * @since 1.0.0
 */
public enum PayState {
    /**
     * 未支付
     */
    UNPAID(0),
    /**
     * 已支付
     */
    PAID(1),
    /**
     * 已退款
     */
    REFUND(2),
    /**
     * 已取消
     */
    CANCEL(3),
    /**
     * 已关闭
     */
    CLOSE(4);

    private final int value;

    PayState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
