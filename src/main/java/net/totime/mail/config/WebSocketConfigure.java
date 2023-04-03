package net.totime.mail.config;

import net.totime.mail.socket.SocketHandler;
import net.totime.mail.socket.SocketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/01
 * @description WebSocket配置类
 * @since 1.0.0
 */
@Configuration
public class WebSocketConfigure implements WebSocketConfigurer {
    @Resource
    private SocketInterceptor socketInterceptor;
    @Resource
    private SocketHandler socketHandler;

    /**
     * 服务器端点配置
     *
     * @return {@link ServerEndpointExporter} Socket服务端点配置
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 注册Socket处理器
     *
     * @param registry 注册对象
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(socketHandler, "/api/ws/{id}")
                .addInterceptors(socketInterceptor)
                .setAllowedOrigins("*");
    }
}