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
 * @date 2023/06/20
 * @description 用户状态枚举
 * @since 1.0.0
 */
public enum UserState {
    /**
     * 正常
     */
    NORMAL(1, "正常"),
    /**
     * 已删除
     */
    DELETED(-1, "已删除"),
    /**
     * 已冻结
     */
    FROZEN(0, "已冻结");

    private final Integer code;
    private final String desc;

    UserState(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
