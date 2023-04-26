package net.totime.mail.socket;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SocketResource {
    @Resource
    private SimpMessagingTemplate smt;

//    @SendTo("/topic/pay")
//    @MessageMapping("/pay")
    public String pay(String msg) {
        return msg;
    }
}
