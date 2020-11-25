package com.server.mp.server.wsconnect;

import com.google.gson.Gson;
import com.server.mp.server.config.BaseMessage;
import com.server.mp.server.config.CommandMessage;
import com.sun.xml.internal.rngom.parse.host.Base;
import com.sun.xml.internal.ws.resources.SenderMessages;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WsClientManager {
    public static Map<String, Session> clientMap = new HashMap<>();
    public static Map<String, Session> adminMap = new HashMap<>();

    public static void onMessage(Session session, String messageStr) {
        if ("pi".equals(messageStr)) {
            System.out.println("pi");
            sendToBySession(session, "po");
            return;
        }
        System.out.println("onMessage:" + messageStr);
        if (messageStr != null && !messageStr.trim().equals("")) {
            BaseMessage message = new Gson().fromJson(messageStr, BaseMessage.class);
            if (message != null && message.getType() != null) {
                switch (message.getType()) {
                    case "amdinlogin"://管理员登录
                        System.out.println("管理员登录");
                        String adminName = message.getClientName();
                        String password = "";
                        Map<String, Object> map = message.getMsg();
                        if (map != null && map.containsKey("password")) {
                            password = (String) map.get("password");
                        }
                        //校验正确性 FIXME 暂时内存写死
                        if ("admin".equals(adminName) && "admin".equals(password)) {
                            if (!adminMap.containsKey(adminName)) {
                                adminMap.put(adminName, session); //这个应该是单独登录的
                            } else {
                                if (session != adminMap.get(adminName)) {
                                    try {
                                        adminMap.get(adminName).close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    adminMap.put(adminName, session);
                                }
                            }
                            System.out.println("登录成功");
                            sendToBySession(session, BaseMessage.replay(message, "登录成功"));
                            return;
                        }
                        System.out.println("登录失败");
                        sendToBySession(session, BaseMessage.replay(message, "登录失败"));
                        break;
                    case "replay"://客户端回应
                        synchronized (WsClientManager.class) {
                            clientMap.put(message.getClientName(), session);
                        }

                        break;
                    case "command":
                        CommandMessage commandMessage = new Gson().fromJson(messageStr, CommandMessage.class);
                        List<String> clientList = commandMessage.getMsg().getSendToList();
                        clientList.forEach(item -> sendTo(item, new Gson().toJson(commandMessage.getMsg().getSendCommand())));
                        break;
                    case "allclient":
                        if (adminMap.containsKey(message.getClientName())) {
                            //如果改管理员存在
                            //TODO 校验token
                            synchronized (WsClientManager.class) {
                                removeOffline();
                                sendToBySession(session, BaseMessage.replay(message, clientMap.keySet()));
                            }
                        }
                        break;
                }


//                String name = message.getClientName();
                //是否是管理员的名字

                //是否是客户列表的名字

                //都不是则不理会这条消息


            }
        }
    }

    public static void onError(Session session, Throwable throwable) {
        synchronized (WsClientManager.class) {
            for (Map.Entry<String, Session> s : adminMap.entrySet()) {
                if (s.getValue() == session) {
                    adminMap.remove(s.getKey());
                    return;
                }
            }

            for (Map.Entry<String, Session> s : clientMap.entrySet()) {
                if (s.getValue() == session) {
                    clientMap.remove(s.getKey());
                    return;
                }
            }

        }
    }

    /**
     * 将消息发送给 '所有' 管理员
     *
     * @param msg
     */
    private static void sendToAdmin(String msg) {
        for (Session s : adminMap.values()) {
            if (s != null && s.isOpen()) {
                try {
                    s.getAsyncRemote().sendText(msg);
                } catch (Exception e) {
                }
            } else {
                //如何移除该条Session

            }
        }
    }

    private static void removeOffline() {
        for (Map.Entry<? extends String, ? extends Session> e : clientMap.entrySet()) {
            if (!e.getValue().isOpen()) {
                clientMap.remove(e.getKey());
            }
        }
    }

    /**
     * 将消息发送给 '指定' 管理员
     *
     * @param adminName
     * @param msg
     */
    private static void sendToAdminByAdminName(String adminName, String msg) {
        if (adminMap.containsKey(adminName)) {
            if (adminMap.get(adminName).isOpen()) {
                try {
                    adminMap.get(adminName).getBasicRemote().sendText(msg);
                } catch (Exception e) {
                }
            } else {
                //如何移除该条Session

            }
        }
    }

    private static void sendTo(String name, String msg) {
        synchronized (WsClientManager.class) {
            if (adminMap.containsKey(name)) {
                if (adminMap.get(name).isOpen()) {
                    try {
                        adminMap.get(name).getBasicRemote().sendText(msg);
                        return;
                    } catch (Exception e) {
                    }
                } else {
                    //如何移除该条Session

                }
            }
            if (clientMap.containsKey(name)) {
                if (clientMap.get(name).isOpen()) {
                    try {
                        clientMap.get(name).getBasicRemote().sendText(msg);
                    } catch (Exception e) {
                    }
                } else {
                    //如何移除该条Session
                }
            }
        }
    }


    private static void sendToBySession(Session session, String msg) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
