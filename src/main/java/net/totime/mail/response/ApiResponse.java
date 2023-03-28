package net.totime.mail.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author JanYork
 * @date 2023/3/8 15:49
 * @description 统一返回结果
 */
@Data
@ApiModel(value = "统一收集数据并返回结果")
public class ApiResponse<T> {
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private int code;
    /**
     * 返回信息
     */
    @ApiModelProperty(value = "接口消息")
    private String message;
    /**
     * 返回数据
     */
    @ApiModelProperty(value = "接口数据")
    private T data;

    public ApiResponse<T> code(int code) {
        this.code = code;
        return this;
    }

    public ApiResponse<T> message(String message) {
        this.message = message;
        return this;
    }

    public ApiResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse() {
    }

    /**
     * 默认成功返回结果
     *
     * @param data 获取的数据
     * @param <T>  泛型
     * @return 成功结果
     */
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, "success", data);
    }

    /**
     * 默认失败返回结果
     *
     * @param data 获取的数据
     * @param <T>  泛型
     * @return 成功结果
     */
    public static <T> ApiResponse<T> fail(T data) {
        return new ApiResponse<>(500, "fail", data);
    }
}