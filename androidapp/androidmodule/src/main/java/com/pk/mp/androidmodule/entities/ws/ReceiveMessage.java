package com.pk.mp.androidmodule.entities.ws;

import java.util.Map;

public class ReceiveMessage {
    private String name;
    private String type;
    private Map<String,String> msg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
