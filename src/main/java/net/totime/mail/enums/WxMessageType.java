package net.totime.mail.enums;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/12
 * @description 微信消息类型枚举
 * @see Enum
 * @since 1.0.0
 */
public enum WxMessageType {
    /**
     * 文本消息
     */
    TEXT("text"),
    /**
     * 事件推送
     */
    EVENT("event"),
    /**
     * 图片消息
     */
    IMAGE("image"),
    /**
     * 语音消息
     */
    VOICE("voice"),
    /**
     * 视频消息
     */
    VIDEO("video"),
    /**
     * 小视频消息
     */
    SHORTVIDEO("shortvideo"),
    /**
     * 地理位置消息
     */
    LOCATION("location"),
    /**
     * 链接消息
     */
    LINK("link");
    private String type;

    WxMessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
