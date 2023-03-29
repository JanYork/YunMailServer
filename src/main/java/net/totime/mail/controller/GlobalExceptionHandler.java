package net.totime.mail.controller;

import cn.dev33.satoken.exception.*;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.kaptcha.exception.KaptchaException;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.exception.RateLimiterException;
import net.totime.mail.response.ApiResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/3/8 15:43
 * @description 全局异常处理程序
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 空指针异常处理程序
     *
     * @param e 异常
     * @return {@link ApiResponse}<{@link String}> 返回错误结果
     */
    @ExceptionHandler(NullPointerException.class)
    public ApiResponse<String> nullPointerExceptionHandler(NullPointerException e) {
        e.printStackTrace();
        return new ApiResponse<String>().code(500).message("null pointer is error").data("空指针异常");
    }

    /**
     * 阿里云短信客户端异常处理程序
     *
     * @return {@link ApiResponse}<{@link String}> 返回错误结果
     */
    @ExceptionHandler(ClientException.class)
    public ApiResponse<String> clientExceptionHandler() {
        return new ApiResponse<String>().code(500).message("ali sms is error").data("阿里云短信异常");
    }

    /**
     * 参数校验异常处理程序
     *
     * @param e 异常
     * @return {@link ApiResponse}<{@link String}> 返回错误结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String msg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return new ApiResponse<String>().code(500).message(msg).data("参数校验异常");
    }

    /**
     * 限流器异常处理程序
     *
     * @param e 异常
     * @return {@link ApiResponse}<{@link String}>
     */
    @ExceptionHandler(RateLimiterException.class)
    public ApiResponse<String> rateLimiterExceptionHandler(RateLimiterException e) {
        return new ApiResponse<String>().code(500).message(e.getMsg()).data("限流器异常");
    }

    /**
     * 未登录异常处理程序
     *
     * @param e 异常
     * @return {@link ApiResponse}<{@link String}> 返回错误结果
     */
    @ExceptionHandler(NotLoginException.class)
    public ApiResponse<String> handlerException(NotLoginException e) {
        e.printStackTrace();
        return ApiResponse.fail(e.getMessage());
    }

    /**
     * 缺少权限异常处理程序
     *
     * @param e 异常
     * @return {@link ApiResponse}<{@link String}> 返回错误结果
     */
    @ExceptionHandler(NotPermissionException.class)
    public ApiResponse<String> handlerException(NotPermissionException e) {
        e.printStackTrace();
        return ApiResponse.fail("缺少权限：" + e.getPermission());
    }

    /**
     * 缺少角色异常处理程序
     *
     * @param e 异常
     * @return {@link ApiResponse}<{@link String}> 返回错误结果
     */
    @ExceptionHandler(NotRoleException.class)
    public ApiResponse<String> handlerException(NotRoleException e) {
        e.printStackTrace();
        return ApiResponse.fail("缺少角色：" + e.getRole());
    }

    /**
     * 二级认证校验失败异常处理程序
     *
     * @param e 异常
     * @return {@link ApiResponse}<{@link String}> 返回错误结果
     */
    @ExceptionHandler(NotSafeException.class)
    public ApiResponse<String> handlerException(NotSafeException e) {
        e.printStackTrace();
        return ApiResponse.fail("二级认证校验失败：" + e.getService());
    }

    /**
     * 服务封禁异常处理程序
     *
     * @param e 异常
     * @return {@link ApiResponse}<{@link String}> 返回错误结果
     */
    @ExceptionHandler(DisableServiceException.class)
    public ApiResponse<String> handlerException(DisableServiceException e) {
        e.printStackTrace();
        return ApiResponse.fail("当前账号 " + e.getService() + " 服务已被封禁 (level=" + e.getLevel() + ")：" + e.getDisableTime() + "秒后解封");
    }

    /**
     * HttpBasic校验失败异常处理程序
     *
     * @param e 异常
     * @return {@link ApiResponse}<{@link String}> 返回错误结果
     */
    @ExceptionHandler(NotBasicAuthException.class)
    public ApiResponse<String> handlerException(NotBasicAuthException e) {
        e.printStackTrace();
        return ApiResponse.fail(e.getMessage());
    }

    /**
     * 全局通用异常处理程序
     *
     * @param e 异常
     * @return {@link ApiResponse}<{@link String}> 返回通用错误结果
     */
    @ExceptionHandler(GloballyUniversalException.class)
    public ApiResponse<String> globallyUniversalExceptionHandler(GloballyUniversalException e) {
        return new ApiResponse<String>().code(e.getCode()).message(e.getMsg()).data(e.getMsg());
    }

    /**
     * Kaptcha异常处理程序
     *
     * @param kaptchaException kaptcha异常
     * @return {@link ApiResponse}<{@link String}> 返回错误结果
     */
    @ExceptionHandler(value = KaptchaException.class)
    public ApiResponse<String> kaptchaExceptionHandler(KaptchaException kaptchaException) {
        if (kaptchaException instanceof KaptchaIncorrectException) {
            return ApiResponse.fail("验证码不正确");
        } else if (kaptchaException instanceof KaptchaNotFoundException) {
            return ApiResponse.fail("验证码未找到");
        } else if (kaptchaException instanceof KaptchaTimeoutException) {
            return ApiResponse.fail("验证码已过期");
        } else {
            return ApiResponse.fail("验证码渲染错误");
        }
    }

    /**
     * 其它所有异常处理程序
     *
     * @param e 异常
     * @return {@link ApiResponse}<{@link String}> 返回错误结果
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handlerException(Exception e) {
        e.printStackTrace();
        return ApiResponse.fail(e.getMessage());
    }
}