package cn.totime.mq.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @author JanYork
 * @date 2023/1/3 13:47
 * @description 邮件消费者
 */
@RocketMQMessageListener(topic = "mail_topic", consumerGroup = "mail_group")
public class MailConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("邮件消费端接收到消息：" + message);
    }
}
