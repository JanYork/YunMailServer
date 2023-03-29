package net.totime.mail.exception;

/**
 * @author JanYork
 * @date 2023/3/16 17:09
 * @description 全局通用运行时异常抛出类
 */
public class GloballyUniversalException extends RuntimeException{

    private static final long serialVersionUID = 8135729914267747431L;

    private Integer code;

    private String msg;

    public GloballyUniversalException(Integer code, String msg) {
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