package net.totime.mail.socket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/06
 * @description Socket资源处理
 * @since 1.0.0
 */
@RestController
public class SocketResource {
    @Resource
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 订阅消息
     *
     * @param principal 用户信息
     * @return 消息
     */
    @SubscribeMapping("/topic/{topic}")
    public String subscribe(@DestinationVariable String topic, Principal principal) {
        return "欢迎" + principal.getName() + "订阅" + topic;
    }

    /**
     * 发送消息
     *
     * @param principal 用户信息
     * @return 消息
     */
    @MessageMapping("/topic/{topic}")
    @SendTo("/topic/{topic}")
    public String send(@DestinationVariable String topic, Principal principal) {
        return "欢迎" + principal.getName() + "发送" + topic;
    }

    /**
     * 接收消息
     *
     * @param topic     主题
     * @param principal 主要
     */
    @MessageMapping("/app/{topic}")
    public void receive(@DestinationVariable String topic, Principal principal) {
        messagingTemplate.convertAndSend("/topic/" + topic, "欢迎" + principal.getName() + "发送" + topic);
    }
}