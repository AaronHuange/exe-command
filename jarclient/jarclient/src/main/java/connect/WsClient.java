package connect;

import commend.Commend;
import entities.SendMessage;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.UUID;

public class WsClient extends WebSocketClient {

    public static WsClient wsClient;
    private static String staticUrl;
    private static String clientName;


    public static WsClient get(String url) {
        staticUrl = url;
        if (wsClient == null) {
            synchronized (WsClient.class) {
                if (wsClient == null) {
                    try {
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

    public static WsClient get() {
        return get("ws://127.0.0.1:8080/chat");
//        return get("ws://123.207.136.134:9010/ajaxchattest");
    }


    private WsClient(URI serverUri) {
        super(serverUri);
        if (clientName == null || "".equals(clientName.trim())) {
            clientName = UUID.randomUUID().toString();
        }
    }

    public void onOpen(ServerHandshake serverHandshake) {
        send("重新连接");
    }

    public void onMessage(String s) {
        if (s != null && !"".equals(s.trim())) {
            if ("pong".equals(s)) {
                //如果是pong消息
                sendPingDelay();
                return;
            }
            Commend.parseCommand(s);
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
                if (wsClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
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


    @Override
    public void send(String text) throws NotYetConnectedException {
        if (wsClient.getReadyState().equals(READYSTATE.OPEN)) {
            super.send(SendMessage.replay(clientName, "replay", text));
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
