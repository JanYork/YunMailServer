package net.totime.mail.annotation;

import cn.dev33.satoken.annotation.SaCheckLogin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/01
 * @description 管理员登录校验注解
 * @since 1.0.0
 */
@SaCheckLogin(type = "admin")
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE})
public @interface SaAdminCheckLogin {
}