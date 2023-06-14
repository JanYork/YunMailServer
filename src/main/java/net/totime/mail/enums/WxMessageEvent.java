package net.totime.mail.enums;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/12
 * @description 微信消息事件类型枚举
 * @since 1.0.0
 */
public enum WxMessageEvent {
    /**
     * 关注事件
     */
    SUBSCRIBE("subscribe"),
    /**
     * 取消关注事件
     */
    UNSUBSCRIBE("unsubscribe"),
    /**
     * 点击菜单
     */
    CLICK("CLICK"),
    /**
     * 点击菜单跳转链接
     */
    VIEW("VIEW"),
    /**
     * 扫码推事件且弹出“消息接收中”提示框的事件
     */
    SCANCODE_PUSH("scancode_push"),
    /**
     * 未知扫码事件
     */
    @Deprecated
    SCANCODE_WAITMSG("scancode_waitmsg"),
    /**
     * 已关注用户扫描带参数二维码
     */
    SCAN("SCAN"),
    /**
     * 上报地理位置
     */
    LOCATION("LOCATION");

    private String event;

    WxMessageEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
