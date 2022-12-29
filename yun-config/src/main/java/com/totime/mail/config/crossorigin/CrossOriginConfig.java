package com.totime.mail.config.crossorigin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * @author JanYork
 * @date 2022/12/29 15:08
 * @description 全局跨域配置类
 */
@Configuration
@CrossOrigin
public class CrossOriginConfig {

    /**
     * 配置全局跨域
     *
     * @return 跨域配置对象
     */
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许任何域名使用
        corsConfiguration.addAllowedOrigin("*");
        // 允许任何头
        corsConfiguration.addAllowedHeader("*");
        // 允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod("*");
        // 允许携带cookie
        corsConfiguration.setAllowCredentials(false);
        // 暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        corsConfiguration.addExposedHeader("Authorization");
        corsConfiguration.addExposedHeader("Access-Control-Allow-Origin");
        corsConfiguration.addExposedHeader("Access-Control-Allow-Credentials");
        corsConfiguration.addExposedHeader("Access-Control-Allow-Methods");
        corsConfiguration.addExposedHeader("Access-Control-Allow-Headers");
        corsConfiguration.addExposedHeader("Access-Control-Max-Age");
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     *
     * @return 跨域过滤器对象
     */
    @Bean
    public CorsFilter corsFilter() {
        // 创建url跨域配置对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 配置所有请求，并将配置对象放入
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}


