package net.totime.mail.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.strategy.SaStrategy;
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
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    /**
     * 重写Sa-Token的注解处理器，增加注解合并功能
     */
    @Autowired
    public void rewriteSaStrategy() {
        SaStrategy.me.getAnnotation = AnnotatedElementUtils::getMergedAnnotation;
    }
}