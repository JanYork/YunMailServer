package net.totime.mail.socket;

import cn.dev33.satoken.stp.StpUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/20
 * @description Socket拦截器
 * @see ChannelInterceptor
 * @since 1.0.0
 */
@Component
public class AuthChannelInterceptor implements ChannelInterceptor {
    /**
     * 在消息发送到通道之前调用
     *
     * @param message 消息
     * @param channel 通道
     * @return {@link Message}<{@link ?}>
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        //判断是否首次连接
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("token");
            if (StringUtils.isNotBlank(token)) {
                Long userId = Long.parseLong((String) StpUtil.getLoginIdByToken(token));
                if (ObjectUtils.isNotEmpty(userId)) {
                    Principal principal = userId::toString;
                    accessor.setUser(principal);
                    return message;
                }
            }
            throw new RuntimeException("token无效");
        }
        return message;
    }
}
