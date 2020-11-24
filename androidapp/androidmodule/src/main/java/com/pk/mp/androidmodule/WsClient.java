package com.pk.mp.androidmodule;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.pk.mp.androidmodule.entities.ws.SendMessage;
import com.pk.mp.androidmodule.utils.Command;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WsClient extends WebSocketClient {

    private static WsClient wsClient;
    private static String staticUrl;
    private static String clientName;
    private static Context innerContext;

    public static WsClient get(Context context, String url) {
        innerContext = context;
        if (wsClient == null) {
            synchronized (WsClient.class) {
                if (wsClient == null) {
                    try {
                        staticUrl = url;
                        wsClient = new WsClient(new URI(url));
                        wsClient.connect();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return wsClient;
    }

    public static WsClient get(Context context) {
        innerContext = context;
        if (TextUtils.isEmpty(staticUrl)) {
            return get(context, "ws://192.168.1.51:8080/chat");
        }
        return get(context, staticUrl);
    }


    private WsClient(URI serverUri) {
        super(serverUri);
        if (clientName == null || "".equals(clientName.trim())) {
            clientName = UUID.randomUUID().toString();
        }
    }

    public void onOpen(ServerHandshake serverHandshake) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setType("replay");
        sendMessage.setClientName(clientName);
        Map<String,String> map=new HashMap<>();
        map.put("tip","重新连接");
        sendMessage.setMsg(map);
        send(new Gson().toJson(sendMessage));
    }

    public void onMessage(String s) {
        if (s != null && !"".equals(s.trim())) {
            if ("pong".equals(s)) {
                //如果是pong消息
                sendPingDelay();
                return;
            }
            Command.parseCommand(innerContext, s);
        }
    }

    public void onClose(int i, String s, boolean b) {
        reConnect();
    }

    public void onError(Exception e) {
        reConnect();
    }

    private void reConnect() {
        synchronized (WsClient.class) {
            if (wsClient != null) {
                if (wsClient.getReadyState().equals(ReadyState.OPEN)) {
                    return;
                }
                wsClient.close();
                wsClient = null;
            }

            if (staticUrl != null && !staticUrl.trim().equals("")) {
                try {
                    wsClient = new WsClient(new URI(staticUrl));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            if (wsClient != null) {
                try {
                    System.out.println("意外断开开始重连");
                    wsClient.connect();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IllegalStateException e) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException t) {
                        e.printStackTrace();
                    }
                    reConnect();
                }
            }
        }
    }

    public void send(Map<String, String> map) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setType("replay");
        sendMessage.setClientName(clientName);
        sendMessage.setMsg(map);
        send(new Gson().toJson(sendMessage));
    }

    @Override
    public void send(String text) throws NotYetConnectedException {
        if (wsClient.getReadyState().equals(ReadyState.OPEN)) {
            super.send(text);
        }
    }

    private void sendPingDelay() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                send("ping");
            }
        }).start();
    }
}
