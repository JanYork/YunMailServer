//package net.totime.mail.config;
//
//import net.totime.mail.interceptor.LoginInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.annotation.Resource;
//
///**
// * @author JanYork
// * @version 1.0.0
// * @date 2023/04/22
// * @description Web配置
// * @see WebMvcConfigurer
// * @since 1.0.0
// */
//@Configuration
//public class WebConfigurer implements WebMvcConfigurer {
//    @Resource
//    private LoginInterceptor loginInterceptor;
//
//    /**
//     * 添加拦截器
//     *
//     * @param registry 拦截器注册器
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor)
//                // 拦截所有请求，这里配置你要拦截的页面，你也可以试试下面那种方法
//                .addPathPatterns("/**");
//    }
//
////    @Override
////    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(loginInterceptor)
////                // 拦截除了login和error之外的所有请求
////                .excludePathPatterns("/login", "/error");
////    }
//
//}
