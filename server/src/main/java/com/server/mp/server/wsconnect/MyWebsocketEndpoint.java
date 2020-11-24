package com.server.mp.server.wsconnect;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

@ServerEndpoint(value = "/chat")
@Component
public class MyWebsocketEndpoint {

    @OnOpen
    public void onOpen(Session session) {
    }

    @OnClose
    public void onClose() {
    }

    @OnMessage
    public void onMessage(Session session, String message) {

        System.out.println("收到消息" + message);
    }


    private static ArrayList<String> otherInit = new ArrayList<>();


}
