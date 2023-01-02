package cn.totime.core.enums;

/**
 * @author JanYork
 * @date 2023/01/02 15:51
 * @description 全局接口返回信息状态码
 */
public enum ApiHttpCodeEnum {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 失败
     */
    FAIL(400, "失败"),
    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED(401, "未认证(签名错误)"),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");

    private final Integer code;
    private final String msg;

    ApiHttpCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ApiHttpCodeEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}