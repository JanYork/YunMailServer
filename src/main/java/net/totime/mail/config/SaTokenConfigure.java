/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaStrategy;
import net.totime.mail.security.StpAdminUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/26
 * @description Sa-Token 配置类
 * @see WebMvcConfigurer
 * @since 1.0.0
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    /**
     * 注册sa-token的拦截器
     * <p>
     * 详情见文档：<a href="https://sa-token.cc/doc.html#/use/route-check">拦截器</a> 部分
     * </p>
     *
     * @param registry 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> {
                    SaRouter.match("/admin/**", r -> StpAdminUtil.checkLogin());
                    SaRouter.match("/api/**", r -> StpUtil.checkLogin());
                    SaRouter.match("/common/**").check(r -> {
                        if (StpAdminUtil.isLogin() || StpUtil.isLogin()) {
                            return;
                        }
                        throw new NotLoginException("未登录任何账户", null, null);
                    });
                    SaRouter.match("/**").match("*.html", "*.css", "*.js", "*.yml", "*.p12", "*.java").check(
                            () -> {
                                throw new RuntimeException("恶意请求");
                            }
                    );
                })).addPathPatterns("/**")
                .excludePathPatterns(
                        "/admin/login",
                        "/login",
                        "/admin/v2/**",
                        "/admin/v1/**",
                        "/api/v2/**",
                        "/api/v1/**"
                );
    }

    /**
     * 重写Sa-Token的注解处理器，增加注解合并功能
     */
    @Autowired
    public void rewriteSaStrategy() {
        SaStrategy.me.getAnnotation = AnnotatedElementUtils::getMergedAnnotation;
    }
}
