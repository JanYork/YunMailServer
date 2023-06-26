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
 * @date 2023/06/26
 * @description 实名认证状态
 * @see Enum
 * @since 1.0.0
 */
public enum IdCardAuthStatus {
    /**
     * 未认证
     */
    UNAUTHENTICATED(0),
    /**
     * 已认证
     */
    AUTHENTICATED(1),
    /**
     * 待审核
     */
    PENDING(2);
    private final int code;

    IdCardAuthStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
