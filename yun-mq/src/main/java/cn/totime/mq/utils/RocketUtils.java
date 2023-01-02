package cn.totime.mq.utils;

import cn.totime.mq.enums.RocketTopicEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author JanYork
 * @date 2023/1/2 20:05
 * @description RocketMQ通用辅助工具类
 */
@Component
@Slf4j
public class RocketUtils {
    @Autowired
    private RocketMQTemplate rocketTemplate;

    @PostConstruct
    public void init() {
        log.info("---Rocket通用工具初始化---");
    }

    /**
     * 发送异步消息
     *
     * @param topic   消息Topic
     * @param message 消息实体
     */
    public void asyncSend(RocketTopicEnums topic, Message<?> message) {
        log.info("发送异步消息，topic：{}，message：{}", topic.getTopic(), message);
        asyncSend(topic.getTopic(), message, getDefaultSendCallBack());
    }


    /**
     * 发送异步消息
     *
     * @param topic        消息Topic
     * @param message      消息实体
     * @param sendCallback 回调函数
     */
    public void asyncSend(RocketTopicEnums topic, Message<?> message, SendCallback sendCallback) {
        log.info("发送异步消息，topic：{}，message：{}", topic.getTopic(), message);
        asyncSend(topic.getTopic(), message, sendCallback);
    }

    /**
     * 发送异步消息
     *
     * @param topic   消息Topic
     * @param message 消息实体
     */
    public void asyncSend(String topic, Message<?> message) {
        log.info("发送异步消息，topic：{}，message：{}", topic, message);
        rocketTemplate.asyncSend(topic, message, getDefaultSendCallBack());
    }

    /**
     * 发送异步消息
     *
     * @param topic        消息Topic
     * @param message      消息实体
     * @param sendCallback 回调函数
     */
    public void asyncSend(String topic, Message<?> message, SendCallback sendCallback) {
        log.info("发送异步消息，topic：{}，message：{}", topic, message);
        rocketTemplate.asyncSend(topic, message, sendCallback);
    }

    /**
     * 发送异步消息
     *
     * @param topic        消息Topic
     * @param message      消息实体
     * @param sendCallback 回调函数
     * @param timeout      超时时间
     */
    public void asyncSend(String topic, Message<?> message, SendCallback sendCallback, long timeout) {
        log.info("发送异步消息，topic：{}，message：{}", topic, message);
        rocketTemplate.asyncSend(topic, message, sendCallback, timeout);
    }

    /**
     * 发送异步消息
     *
     * @param topic        消息Topic
     * @param message      消息实体
     * @param sendCallback 回调函数
     * @param timeout      超时时间
     * @param delayLevel   延迟消息的级别
     */
    public void asyncSend(String topic, Message<?> message, SendCallback sendCallback, long timeout, int delayLevel) {
        log.info("发送异步消息，topic：{}，message：{}", topic, message);
        rocketTemplate.asyncSend(topic, message, sendCallback, timeout, delayLevel);
    }

    /**
     * 发送顺序消息
     *
     * @param message 消息实体
     * @param topic   消息Topic
     * @param hashKey 顺序消息的hashKey
     */
    public void syncSendOrderly(RocketTopicEnums topic, Message<?> message, String hashKey) {
        log.info("发送同步消息，topic：{}，message：{}", topic.getTopic(), message);
        syncSendOrderly(topic.getTopic(), message, hashKey);
    }


    /**
     * 发送顺序消息
     *
     * @param message 消息实体
     * @param topic   消息Topic
     * @param hashKey 顺序消息的hashKey
     */
    public void syncSendOrderly(String topic, Message<?> message, String hashKey) {
        log.info("发送同步消息，topic：{}，message：{}", topic, message);
        rocketTemplate.syncSendOrderly(topic, message, hashKey);
    }

    /**
     * 发送顺序消息
     *
     * @param message 消息实体
     * @param topic   消息Topic
     * @param hashKey 顺序消息的hashKey
     * @param timeout 超时时间
     */
    public void syncSendOrderly(String topic, Message<?> message, String hashKey, long timeout) {
        log.info("发送同步消息，topic：{}，message：{}", topic, message);
        rocketTemplate.syncSendOrderly(topic, message, hashKey, timeout);
    }

    /**
     * 获取默认的消息回调函数
     *
     * @return SendCallback对象
     */
    private SendCallback getDefaultSendCallBack() {
        return new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("发送消息成功，sendResult：{}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("发送消息失败，throwable：{}", throwable.getMessage());
            }
        };
    }

    @PreDestroy
    public void destroy() {
        log.info("---Rocket通用工具被销毁---");
    }
}