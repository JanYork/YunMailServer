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
 * @date 2023/06/24
 * @description 支付轮询场景枚举
 * @see Enum
 * @since 1.0.0
 */
public enum PayPollKey {
    /**
     * 默认
     */
    DEFAULT("default_scenario", 180L);
    /**
     * 场景
     */
    private final String key;
    /**
     * 超时时间
     */
    private final Long expire;

    PayPollKey(String key, Long expire) {
        this.key = key;
        this.expire = expire;
    }

    public String getKey() {
        return key;
    }

    public Long getExpire() {
        return expire;
    }

    public static PayPollKey getPayPollKey(String key) {
        for (PayPollKey payPollKey : PayPollKey.values()) {
            if (payPollKey.getKey().equals(key)) {
                return payPollKey;
            }
        }
        return null;
    }
}
