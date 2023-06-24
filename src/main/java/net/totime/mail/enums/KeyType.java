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
 * @description 缓存key枚举
 * @see Enum
 * @since 1.0.0
 */
public enum KeyType {
    /**
     * 一般验证码
     */
    NORMAL("normal:", "一般验证码"),

    /**
     * 开放接口验证码
     */
    OPEN_PHONE("open:", "开放接口验证码"),
    /**
     * 开放接口验证码计数器
     */
    OPEN_PHONE_INCR("open_incr:", "开放接口验证码计数器"),
    /**
     * 开放接口邮箱验证码
     */
    OPEN_EMAIL("m_open:", "开放接口邮箱验证码"),
    /**
     * 开放接口邮箱验证码计数器
     */
    OPEN_EMAIL_INCR("m_open_incr:", "开放接口邮箱验证码计数器"),
    /**
     * 修改密码验证码
     */
    CHANGE_PASSWORD("change:", "修改密码验证码"),
    /**
     * 绑定手机号
     */
    BIND_PHONE("bind_phone:", "绑定手机号"),
    /**
     * 改绑手机号
     */
    CHANGE_PHONE("change_phone:", "改绑手机号"),
    /**
     * 改绑手机号验证通过标识
     */
    CHANGE_PHONE_FLAG("change_phone_flag:", "改绑手机号验证通过标识"),;

    private final String key;
    private final String desc;

    KeyType(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
