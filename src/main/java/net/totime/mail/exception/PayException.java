/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.exception;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/19
 * @description 支付异常
 * @see RuntimeException
 * @since 1.0.0
 */
public class PayException extends RuntimeException{
    private static final long serialVersionUID = -8998724105151684295L;
    private String payType;
    private String orderNo;

    public PayException(String payType, String orderNo) {
        this.payType = payType;
        this.orderNo = orderNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
