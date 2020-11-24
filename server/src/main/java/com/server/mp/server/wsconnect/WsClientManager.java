package com.server.mp.server.wsconnect;

import com.google.gson.Gson;
import com.server.mp.server.config.BaseMessage;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class WsClientManager {
    public static Map<String, Session> clientMap = new HashMap<>();
    public static Map<String, Session> adminMap = new HashMap<>();

    public static void onMessage(Session session, String messageStr) {
        System.out.println("onMessage:" + messageStr);
        if (messageStr != null && !messageStr.trim().equals("")) {
            BaseMessage message = new Gson().fromJson(messageStr, BaseMessage.class);
            if (message != null && message.getType() != null) {
                switch (message.getType()) {
                    case "amdinlogin"://管理员登录
                        System.out.println("管理员登录");
                        String adminName = message.getClientName();
                        String password = "";
                        Map<String, String> map = message.getMsg();
                        if (map != null && map.containsKey("password")) {
                            password = map.get("password");
                        }
                        //校验正确性


                        //验证通过添加到管理员列表
                        if (!adminMap.containsKey(adminName)) {
                            adminMap.put(adminName, session);
                        }
                        break;
                    case "clientlogin"://客户端登录

                        break;
                    case "replae":


                        break;
                    case "command":


                }


//                String name = message.getClientName();
                //是否是管理员的名字

                //是否是客户列表的名字

                //都不是则不理会这条消息


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
