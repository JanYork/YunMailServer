/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.enums;

import java.math.BigDecimal;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/17
 * @description 支付金额
 * @see Enum
 * @since 1.0.0
 */
public enum PayAmount {
    /**
     * 信件价格
     */
    LETTER_PRICE(new BigDecimal("0.01")),
    /**
     * 短信价格
     */
    SMS_PRICE(new BigDecimal("0.01"));

    private BigDecimal amount;

    PayAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
