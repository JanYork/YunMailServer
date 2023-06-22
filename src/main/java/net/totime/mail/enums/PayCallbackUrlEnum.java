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
 * @date 2023/06/17
 * @description 支付回调地址枚举
 * @see Enum
 * @since 1.0.0
 */
public enum PayCallbackUrlEnum {
    /**
     * 微信信件回调地址
     */
    WX_CALLBACK_URL("/wx/callback"),
    /**
     * 支付宝信件回调地址
     */
    ALI_CALLBACK_URL("/ali/callback"),
    /**
     * 微信赞助回调地址
     */
    WX_DONATE_CALLBACK_URL("/wx/sponsor/callback"),
    /**
     * 支付宝赞助回调地址
     */
    ALI_DONATE_CALLBACK_URL("/ali/sponsor/callback"),
    /**
     * 微信心愿回调地址
     */
    WX_WISH_CALLBACK_URL("/wx/wish/callback"),
    /**
     * 支付宝心愿回调地址
     */
    ALI_WISH_CALLBACK_URL("/ali/wish/callback"),
    /**
     * 微信短信回调地址
     */
    WX_SMS_CALLBACK_URL("/wx/msg/callback"),
    /**
     * 支付宝短信回调地址
     */
    ALI_SMS_CALLBACK_URL("/ali/msg/callback");
    private String value;

    PayCallbackUrlEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        String baseUrl = "http://5mxish.natappfree.cc/api/v2/pay";
        return baseUrl + value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
