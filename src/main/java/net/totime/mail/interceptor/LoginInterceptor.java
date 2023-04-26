//package net.totime.mail.interceptor;
//
//import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author JanYork
// * @version 1.0.0
// * @date 2023/04/22
// * @description 登录拦截器
// * @see HandlerInterceptor
// * @since 1.0.0
// */
//@Component
//public class LoginInterceptor  implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
//        boolean isLogin=false;
//        String token = request.getHeader("Authorization");
//        if (StringUtils.hasText(token)) {
//            System.out.println("token = " + token);
//            isLogin = true;
//        }
//        if(!isLogin){
//            response.setContentType("application/json;charset=UTF-8");
//            response.setCharacterEncoding("UTF-8");
//            response.setStatus(401);
//            response.getWriter().write("未通过认证，请在登录页进行登录");
//        }
//        return isLogin;
//    }
//}
