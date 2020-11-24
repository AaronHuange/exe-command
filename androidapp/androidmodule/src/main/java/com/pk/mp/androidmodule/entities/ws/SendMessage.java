package com.pk.mp.androidmodule.entities.ws;

import org.json.JSONObject;

import java.util.Map;

public class SendMessage {
    private String clientName;
    private String type;
    private Map<String,String> msg;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getMsg() {
        return msg;
    }

    public void setMsg(Map<String, String> msg) {
        this.msg = msg;
    }
}
