package com.server.mp.server.wsconnect;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat")
@Component
public class MyWebsocketEndpoint {

    @OnMessage
    public void onMessage(Session session, String message) {
        WsClientManager.onMessage(session,message);
    }

}
