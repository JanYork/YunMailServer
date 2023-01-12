package cn.totime.api.mq;

import cn.totime.mq.enums.RocketTopicEnums;
import cn.totime.mq.utils.RocketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JanYork
 * @date 2023/1/3 13:55
 * @description MQ消息发送控制器(异步)
 */
@RestController
@RequestMapping("/mq/async")
public class AsyncSendController {
    @Autowired
    private RocketUtils rocketUtils;

    @RequestMapping("/sendString")
    public void sendString() {
        Message<String> message = MessageBuilder.withPayload("hello world test1").build();
        rocketUtils.asyncSend(RocketTopicEnums.MAIL_TOPIC, message);
    }
}