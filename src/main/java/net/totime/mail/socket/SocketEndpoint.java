package net.totime.mail.socket;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/01
 * @description Socket服务端点
 * @since 1.0.0
 */
@Component
@ServerEndpoint("/api/ws/{id}")
@Slf4j
public class SocketEndpoint {
    /**
     * 当前在线连接数
     */
    private static int ONLINE_COUNT = 0;
    /**
     * Socket连接对象集合
     */
    private static final ConcurrentHashMap<String, SocketEndpoint> SOCKET_MAP = new ConcurrentHashMap<>();
    /**
     * Socket会话对象
     */
    private Session session;
    /**
     * 会话ID
     */
    private String id;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 会话
     * @param id      id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        this.session = session;
        this.id = id;
        if (SOCKET_MAP.containsKey(id)) {
            SOCKET_MAP.remove(id);
            SOCKET_MAP.put(id, this);
        } else {
            SOCKET_MAP.put(id, this);
            addOnlineCount();
        }
        log.info("用户连接:" + id + ",当前在线人数为:" + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:" + id + ",网络异常!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (SOCKET_MAP.containsKey(id)) {
            SOCKET_MAP.remove(id);
            subOnlineCount();
        }
        log.info("用户退出:" + id + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("用户消息:" + id + ",报文:" + message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSONObject.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId", this.id);
                String toUserId = jsonObject.getString("toUserId");
                //传送给对应toUserId用户的websocket
                if (StringUtils.isNotBlank(toUserId) && SOCKET_MAP.containsKey(toUserId)) {
                    SOCKET_MAP.get(toUserId).sendMessage(jsonObject.toJSONString());
                } else {
                    this.sendMessage("用户" + toUserId + "不在线");
                    log.error("请求的userId:" + toUserId + "不在该服务器上");
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用户异常调用
     *
     * @param error 异常
     */
    @OnError
    public void onError(Throwable error) {
        log.error("用户错误:" + this.id + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     *
     * @param message 消息
     * @throws IOException IO异常
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送自定义消息
     *
     * @param message 消息
     * @param id id
     * @throws IOException IO异常
     */
    public static void sendInfo(String message, @PathParam("id") String id) throws IOException {
        log.info("发送消息到:" + id + "，报文:" + message);
        if (StringUtils.isNotBlank(id) && SOCKET_MAP.containsKey(id)) {
            SOCKET_MAP.get(id).sendMessage(message);
        } else {
            log.error("用户" + id + ",不在线！");
        }
    }

    public static synchronized int getOnlineCount() {
        return ONLINE_COUNT;
    }

    public static synchronized void addOnlineCount() {
        SocketEndpoint.ONLINE_COUNT++;
    }

    public static synchronized void subOnlineCount() {
        SocketEndpoint.ONLINE_COUNT--;
    }
}