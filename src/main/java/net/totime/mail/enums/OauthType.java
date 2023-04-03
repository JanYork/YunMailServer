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
    WECHAT("2", "微信小程序"),
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
    DOUYIN("5", "抖音");

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

    @Override
    public String toString() {
        return "OauthType{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}