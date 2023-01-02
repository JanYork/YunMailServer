package cn.totime.common.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author JanYork
 * @date 2023/1/2 13:58
 * @description 通用返回结果类
 */
@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回内容
     */
    private String msg;

    /**
     * 数据对象
     */
    private T data;

    /**
     * 构造函数
     */
    public ResultVO() {
    }

    /**
     * 构造函数
     *
     * @param code 状态码
     */
    public ResultVO(int code) {
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param msg 返回内容
     */
    public ResultVO(String msg) {
        this.msg = msg;
    }

    /**
     * 构造函数
     *
     * @param data 数据对象
     */
    public ResultVO(T data) {
        this.data = data;
    }

    /**
     * 构造函数
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public ResultVO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 初始化携带参数(code)
     *
     * @param code 状态码
     * @return 自身对象
     */
    public ResultVO<T> code(int code) {
        this.code = code;
        return this;
    }

    /**
     * 初始化携带参数(msg)
     *
     * @param msg 返回内容
     * @return 自身对象
     */
    public ResultVO<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 初始化携带参数(data)
     *
     * @param data 数据对象
     * @return 自身对象
     */
    public ResultVO<T> data(T data) {
        this.data = data;
        return this;
    }

    /**
     * 初始化携带参数(code, msg)
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 自身对象
     */
    public ResultVO<T> codeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    /**
     * 初始化携带参数(code, data)
     *
     * @param code 状态码
     * @param data 数据对象
     * @return 自身对象
     */
    public ResultVO<T> codeData(int code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    /**
     * 初始化携带参数(msg, data)
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 自身对象
     */
    public ResultVO<T> msgData(String msg, T data) {
        this.msg = msg;
        this.data = data;
        return this;
    }

    /**
     * 初始化携带参数(code, msg, data)
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     * @return 自身对象
     */
    public ResultVO<T> codeMsgData(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }

    /**
     * 返回成功
     *
     * @return ResultVO对象
     */
    public ResultVO<T> success() {
        return new ResultVO<>(200, "操作成功", null);
    }

    /**
     * 返回成功
     *
     * @param msg 返回内容
     * @return ResultVO对象
     */
    public ResultVO<T> success(String msg) {
        return new ResultVO<>(200, msg, null);
    }

    /**
     * 返回成功
     *
     * @param data 数据对象
     * @return ResultVO对象
     */
    public ResultVO<T> success(T data) {
        return new ResultVO<>(200, "操作成功", data);
    }

    /**
     * 返回成功
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return ResultVO对象
     */
    public ResultVO<T> success(String msg, T data) {
        return new ResultVO<>(200, msg, data);
    }

    /**
     * 返回失败
     *
     * @return ResultVO对象
     */
    public ResultVO<T> error() {
        return new ResultVO<>(500, "操作失败", null);
    }

    /**
     * 返回失败
     *
     * @param msg 返回内容
     * @return ResultVO对象
     */
    public ResultVO<T> error(String msg) {
        return new ResultVO<>(500, msg, null);
    }

    /**
     * 返回失败
     *
     * @param data 数据对象
     * @return ResultVO对象
     */
    public ResultVO<T> error(T data) {
        return new ResultVO<>(500, "操作失败", data);
    }

    /**
     * 返回失败
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return ResultVO对象
     */
    public ResultVO<T> error(String msg, T data) {
        return new ResultVO<>(500, msg, data);
    }
}