package net.totime.mail.exception;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/24
 * @description 限流异常
 * @see RuntimeException
 * @since 1.0.0
 */
public class RateLimiterException extends RuntimeException {
    private static final long serialVersionUID = -665508842709838904L;
    private Integer code;
    private String msg;

    public RateLimiterException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}