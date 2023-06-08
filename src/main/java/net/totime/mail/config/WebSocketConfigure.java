/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.config;

import net.totime.mail.socket.AuthChannelInterceptor;
import net.totime.mail.socket.SocketInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
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
    @Resource
    private AuthChannelInterceptor authChannelInterceptor;

    @Value("${ws.allowed-origins}")
    private String allowedOrigins;
    @Value("${ws.endpoint}")
    private String endpoint;

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
                addEndpoint(endpoint).
                addInterceptors(socketInterceptor).
                setAllowedOrigins(allowedOrigins).
                withSockJS();
    }

    /**
     * 配置客户端入站通道
     *
     * @param registration 通道注册
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptor);
    }

    /**
     * Configure message broker options.
     *
     * @param registry 消息代理注册
     */
    @Override
    public void configureMessageBroker(@Nonnull MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue", "/third");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }
}
