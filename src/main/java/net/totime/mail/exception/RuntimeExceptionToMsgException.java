/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/03
 * @description 运行时异常且触发短信通知
 * @since 1.0.0
 */
@Slf4j
public class RuntimeExceptionToMsgException extends RuntimeException {
    private static final long serialVersionUID = 672562990424079492L;

    private String msg;
    private final String errorId;
    private String errorDomain;

    public RuntimeExceptionToMsgException(String msg, String errorDomain) {
        super(msg);
        this.msg = msg;
        this.errorDomain = errorDomain;
        this.errorId = UUID.randomUUID().toString();
        log.error("异常ID：{}，异常信息：{}", this.errorId, msg);
    }

    public RuntimeExceptionToMsgException(String msg) {
        super(msg);
        this.msg = msg;
        this.errorDomain = "未定义";
        this.errorId = UUID.randomUUID().toString();
        log.error("异常ID：{}，异常信息：{}", this.errorId, msg);
    }

    public RuntimeExceptionToMsgException(String msg, String errorDomain, RuntimeException e) {
        super(msg);
        this.msg = msg;
        this.errorDomain = "未定义";
        this.errorId = UUID.randomUUID().toString();
        log.error("异常ID：{}，异常信息：{}，错误：{}", this.errorId, msg, e.toString());
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

    public String getErrorDomain() {
        return errorDomain;
    }

    public void setErrorDomain(String errorDomain) {
        this.errorDomain = errorDomain;
    }
}
