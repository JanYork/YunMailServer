package net.totime.mail.handler;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import net.totime.mail.enums.WxMessageEvent;
import net.totime.mail.enums.WxMpSceneEnum;
import org.springframework.stereotype.Component;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/12
 * @description 微信消息处理器
 * @since 1.0.0
 */
@Component
@Slf4j
public class WxMessageHandler {
    /**
     * 消息类型
     */
    String messageType;
    /**
     * 发送用户
     */
    String fromUser;
    /**
     * 接收用户
     */
    String toUser;
    /**
     * 文本
     */
    String text;
    /**
     * 消息对象
     */
    WxMpXmlMessage message;

    /**
     * 微信关注登录前缀
     */
    private final String WX_LOGIN_PREFIX = "qrscene_";

    /**
     * 微信消息处理器
     *
     * @param message 消息对象
     * @return {@link String}
     */
    public String handler(WxMpXmlMessage message) {
        this.messageType = message.getMsgType();
        this.fromUser = message.getFromUser();
        this.toUser = message.getToUser();
        this.text = message.getContent();
        this.message = message;
        switch (messageType) {
            case "event":
                return eventHandler();
            case "text":
                return textHandler();
            case "image":
                return imageHandler();
            case "voice":
                return voiceHandler();
            case "video":
                return videoHandler();
            case "shortvideo":
                return shortVideoHandler();
            case "location":
                return locationHandler();
            case "link":
                return linkHandler();
            default:
                return null;
        }
    }

    /**
     * 事件子处理器
     *
     * @return {@link String}
     */
    private String eventHandler() {
        System.out.println("event：" + message.getEvent());
        // 关注事件
        if (message.getEvent().equals(WxMessageEvent.SUBSCRIBE.getEvent())) {
            String content = "Hi！让小云看看，是哪一个小可爱通过时光机来到了云寄星球？嘿嘿嘿！不要走！快给未来留下点什么吧！我就知道你有很多小秘密，放心写下吧！\n" +
                    "哎呀呀，小云是不会偷听的啦！";
            // 获取场景值
            if (message.getEventKey().equals(WX_LOGIN_PREFIX + WxMpSceneEnum.LOGIN.getScene())) {
                content = content + "\n\n" +
                        "嗯！对啦！你已经成功登录云寄星球，有什么事情可以和小云说哦！";
            }
            WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                    .TEXT()
                    .toUser(fromUser)
                    .fromUser(toUser)
                    .content(content)
                    .build();
            return texts.toXml();
        }
        // 取消关注
        if (message.getEvent().equals(WxMessageEvent.UNSUBSCRIBE.getEvent())) {
            return null;
        }
        // 点击菜单
        if (message.getEvent().equals(WxMessageEvent.CLICK.getEvent())) {
            log.info("用户点击菜单：{}", message.getEventKey());
            return null;
        }
        // 点击菜单
        if (message.getEvent().equals(WxMessageEvent.VIEW.getEvent())) {
            log.info("用户点击菜单：{}", message.getEventKey());
            return null;
        }
        // 已关注用户扫描带参数二维码
        if (message.getEvent().equals(WxMessageEvent.SCANCODE_WAITMSG.getEvent())) {
            log.info("用户扫描二维码：{}", fromUser);
            return null;
        }
        // 扫描带参数二维码事件(除登录)
        if (message.getEvent().equals(WxMessageEvent.SCAN.getEvent())) {
            log.info("用户扫描二维码：{}", fromUser);
            return null;
        }
        // 获取位置信息
        if (message.getEvent().equals(WxMessageEvent.LOCATION.getEvent())) {
            log.info("用户发送位置信息：经度：{}，纬度：{}", message.getLatitude(), message.getLongitude());
            return null;
        }
        return null;
    }

    /**
     * 文本消息子处理器
     *
     * @return {@link String}
     */
    private String textHandler() {
        WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                .TEXT()
                .toUser(fromUser)
                .fromUser(toUser)
                .content("来自2099年的小云看不懂古代文字消息呢！快去找云寄星球的小伙伴帮忙翻译吧！或者等待我帮你转达哦！")
                .build();
        return texts.toXml();
    }

    /**
     * 图片消息子处理器
     *
     * @return {@link String}
     */
    private String imageHandler() {
        WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                .TEXT()
                .toUser(fromUser)
                .fromUser(toUser)
                .content("古记忆碎片？在我们2099年很值钱的啦！快收好，等我帮你找找看，空间读取仪能不能读取到！")
                .build();
        return texts.toXml();
    }

    /**
     * 语音消息子处理器
     *
     * @return {@link String}
     */
    private String voiceHandler() {
        WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                .TEXT()
                .toUser(fromUser)
                .fromUser(toUser)
                .content("古代语言？小云听不懂呢！之前在2050年的时光遗迹里面听到过类似的语言，en...!")
                .build();
        return texts.toXml();
    }

    /**
     * 视频消息子处理器
     *
     * @return {@link String}
     */
    private String videoHandler() {
        WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                .TEXT()
                .toUser(fromUser)
                .fromUser(toUser)
                .content("哇！超级珍贵的古记忆碎片！在我们2099年很值钱的啦！快收好，等我帮你找找看，空间读取仪能不能读取到！")
                .build();
        return texts.toXml();
    }

    /**
     * 小视频消息子处理器
     *
     * @return {@link String}
     */
    private String shortVideoHandler() {
        WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                .TEXT()
                .toUser(fromUser)
                .fromUser(toUser)
                .content("en....小云看不懂呢！")
                .build();
        return texts.toXml();
    }

    /**
     * 地理位置消息子处理器
     *
     * @return {@link String}
     */
    private String locationHandler() {
        WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                .TEXT()
                .toUser(fromUser)
                .fromUser(toUser)
                .content("捕获到了你的位置信息，小云会记住的！嗯？这个地方好熟悉，想不起来了呢！")
                .build();
        return texts.toXml();
    }

    /**
     * 链接消息子处理器
     *
     * @return {@link String}
     */
    private String linkHandler() {
        WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                .TEXT()
                .toUser(fromUser)
                .fromUser(toUser)
                .content("嘿！你的链接里面有什么？小云好奇！但是小云看不到呢！")
                .build();
        return texts.toXml();
    }
}
