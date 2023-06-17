/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.util;

import lombok.Data;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/17
 * @description 描述
 * @since 1.0.0
 */
@Data
public class CheckReturn<T> {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    /**
     * 消息
     */
    private String msg;
    /**
     * 状态
     */
    private Boolean status;
    /**
     * 值对象
     */
    private T value;

    /**
     * 成功
     *
     * @param value 值对象
     */
    public CheckReturn(T value) {
        this.msg = SUCCESS;
        this.status = true;
        this.value = value;
    }

    /**
     * 失败
     */
    public CheckReturn(String msg) {
        this.msg = msg;
        this.status = false;
    }

    /**
     * 成功
     *
     * @param value 值对象
     * @return {@link CheckReturn}<{@link T}>
     */
    public static <T> CheckReturn<T> ok(T value) {
        return new CheckReturn<>(value);
    }

    /**
     * 失败
     *
     * @return {@link CheckReturn}<{@link T}>
     */
    public static <T> CheckReturn<T> fail(String msg) {
        return new CheckReturn<>(msg);
    }
}
