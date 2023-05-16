package net.totime.mail.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/12
 * @description 物流状态枚举
 * @see Enum
 * @since 1.0.0
 */
public enum LogisticsState {
    /**
     * 待揽收
     */
    WAIT_ACCEPT(0, "待揽收"),
    /**
     * 已揽收
     */
    ACCEPT(1, "已揽收"),
    /**
     * 运输中
     */
    TRANSPORT(2, "运输中"),
    /**
     * 派送中
     */
    DELIVERING(3, "派送中"),
    /**
     * 已签收
     */
    DELIVERING_SUCCESS(4, "已签收"),
    /**
     * 已签收
     */
    SIGN(5, "已签收"),
    /**
     * 包裹异常
     */
    SIGN_ERROR(6, "包裹异常");

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String desc;

    LogisticsState(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<Integer, String> getMap() {
        Map<Integer, String> map = new HashMap<>();
        for (LogisticsState item : values()) {
            map.put(item.getCode(), item.getDesc());
        }
        return map;
    }
}
