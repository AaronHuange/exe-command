package com.server.mp.server.config;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class BaseMessage {

    private String clientName;
    private Map<String, Object> msg;
    private String type;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Map<String, Object> getMsg() {
        return msg;
    }

    public void setMsg(Map<String, Object> msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String replay(BaseMessage message, Object msg) {
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setClientName(message.getClientName());
        baseMessage.setType(message.getType());
        Map<String, Object> map = new HashMap<>();
        map.put("tip", msg);
        baseMessage.setMsg(map);
        return new Gson().toJson(baseMessage);
    }

}
