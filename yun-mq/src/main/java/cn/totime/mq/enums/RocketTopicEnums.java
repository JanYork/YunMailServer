package cn.totime.mq.enums;

/**
 * @author JanYork
 * @date 2023/1/2 20:22
 * @description RocketMQ消息Topic枚举
 */
public enum RocketTopicEnums {
    /**
     * 邮件消息
     */
    MAIL_TOPIC("mail_topic", "邮件消息"),

    /**
     * 短信消息
     */
    SMS_TOPIC("sms_topic", "短信消息"),

    /**
     * 通知消息
     */
    NOTICE_TOPIC("notice_topic", "通知消息"),

    /**
     * 其他消息
     */
    OTHER_TOPIC("other_topic", "其他消息");

    private final String topic;
    private final String desc;

    RocketTopicEnums(String topic, String desc) {
        this.topic = topic;
        this.desc = desc;
    }

    public String getTopic() {
        return topic;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "RocketTopicEnums{" +
                "topic='" + topic + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
