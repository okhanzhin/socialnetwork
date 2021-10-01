package com.getjavajob.training.okhanzhin.socialnetwork.webapp.filter;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
public class ChatHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(@NotNull ServerHttpRequest serverHttpRequest, @NotNull ServerHttpResponse serverHttpResponse,
                                   @NotNull WebSocketHandler webSocketHandler, @NotNull Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = request.getServletRequest().getSession();
            map.put("source", session.getAttribute("homeAccount"));
            map.put("chatRoom", session.getAttribute("chatRoom"));
        }

        return true;
    }

    @Override
    public void afterHandshake(@NotNull ServerHttpRequest serverHttpRequest, @NotNull ServerHttpResponse serverHttpResponse,
                               @NotNull WebSocketHandler webSocketHandler, Exception e) {
    }
}
