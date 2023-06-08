/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/12
 * @description 描述
 * @see Enum
 * @since 1.0.0
 */
public enum LogisticsType {
    /**
     * 顺丰快递
     */
    SF("SF", "顺丰快递"),
    /**
     * 圆通快递
     */
    YTO("YTO", "圆通快递"),
    /**
     * 中通快递
     */
    ZTO("ZTO", "中通快递"),
    /**
     * 韵达快递
     */
    YUNDA("YUNDA", "韵达快递"),
    /**
     * 韵达快运
     */
    YDKY("YDKY", "韵达快运"),
    /**
     * 申通快递
     */
    STO("STO", "申通快递"),
    /**
     * 天天快递
     */
    TTKDEX("TTKDEX", "天天快递"),
    /**
     * 京东快递
     */
    JD("JD", "京东快递"),
    /**
     * EMS 邮政
     */
    EMS("EMS", "邮政快递");

    private final String code;
    private final String name;
    private static Map<String, String> map;

    LogisticsType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public static Map<String, String> getMap() {
        if (map != null) {
            return map;
        }
        Map<String, String> map = new HashMap<>();
        for (LogisticsType item : values()) {
            map.put(item.getCode(), item.getName());
        }
        LogisticsType.map = map;
        return map;
    }
}
