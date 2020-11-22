package com.example.mp.server.controller;

import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat")
@Component
public class MyEndpoint {

    @OnMessage
    public void onMessage(Session session, String message) {
        WsClientManager.onMessage(session, message);
    }

}
