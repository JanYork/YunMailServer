package net.totime.mail.config;

import net.totime.mail.socket.SocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/01
 * @description WebSocket配置类
 * @since 1.0.0
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigure implements WebSocketMessageBrokerConfigurer {
    @Resource
    private SocketInterceptor socketInterceptor;

    /**
     * Register STOMP endpoints mapping each to a specific URL and (optionally)
     * enabling and configuring SockJS fallback options.
     *
     * @param registry 注册
     */
    @Override
    public void registerStompEndpoints(@Nonnull StompEndpointRegistry registry) {
        // 配置客户端尝试连接地址
        registry.
                addEndpoint("/ws").
                addInterceptors(socketInterceptor).
        setAllowedOrigins("http://127.0.0.1:5173").
                withSockJS();
    }

    /**
     * Configure message broker options.
     *
     * @param registry 消息代理注册
     */
    @Override
    public void configureMessageBroker(@Nonnull MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }
}