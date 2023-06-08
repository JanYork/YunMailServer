/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.exception;

import java.util.UUID;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/03
 * @description 运行时异常且触发短信通知
 * @since 1.0.0
 */
public class RuntimeExceptionToMsgException extends RuntimeException {
    private static final long serialVersionUID = 672562990424079492L;

    private String msg;
    private final String errorId;

    public RuntimeExceptionToMsgException(String msg) {
        super(msg);
        this.msg = msg;
        this.errorId = UUID.randomUUID().toString();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorId() {
        return errorId;
    }
}
