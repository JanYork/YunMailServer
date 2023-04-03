package net.totime.mail.socket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/01
 * @description Socket拦截器
 * @see HandshakeInterceptor Socket拦截器
 * @since 1.0.0
 */
@Component
public class SocketInterceptor implements HandshakeInterceptor {
    /**
     * Invoked before the handshake is processed.
     *
     * @param request    the current request
     * @param response   the current response
     * @param wsHandler  the target WebSocket handler
     * @param attributes the attributes from the HTTP handshake to associate with the WebSocket
     *                   session; the provided attributes are copied, the original map is not used.
     * @return whether to proceed with the handshake ({@code true}) or abort ({@code false})
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        return false;
    }

    /**
     * Invoked after the handshake is done. The response status and headers indicate
     * the results of the handshake, i.e. whether it was successful or not.
     *
     * @param request   the current request
     * @param response  the current response
     * @param wsHandler the target WebSocket handler
     * @param exception an exception raised during the handshake, or {@code null} if none
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}