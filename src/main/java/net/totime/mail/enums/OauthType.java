/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/02
 * @description 三方登录类型枚举
 * @see Enum
 * @since 1.0.0
 */
public enum OauthType {
    /**
     * QQ
     */
    QQ("1", "QQ"),
    /**
     * 微信小程序
     */
    WECHAT_MINI("2", "微信小程序"),
    /**
     * GITEE
     */
    GITEE("3", "GITEE"),
    /**
     * GITHUB
     */
    GITHUB("4", "GITHUB"),
    /**
     * 抖音
     */
    DOUYIN("5", "抖音"),
    /**
     * 微信公众号
     */
    WECHAT_MP("6", "微信公众号");

    @EnumValue
    private final String type;
    @JsonValue
    private final String name;

    OauthType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
