package com.pk.mp.androidmodule.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.pk.mp.androidmodule.entities.ws.ReceiveMessage;

import java.util.Map;

public class Command {
    public static void parseCommand(Context context, String command) {
        if (!TextUtils.isEmpty(command)) {
            ReceiveMessage receiveMessage = new Gson().fromJson(command, ReceiveMessage.class);
            if (receiveMessage != null) {
                String type = receiveMessage.getType();
                Map<String, String> msg = receiveMessage.getMsg();
                String name = receiveMessage.getName();
                switch (type) {
                    case "open":
                        AppUtil.startApp(context, msg.get("packagename"));
                        break;
                    case "2":
                        break;
                    default:
                }
            }
        }
    }

}
