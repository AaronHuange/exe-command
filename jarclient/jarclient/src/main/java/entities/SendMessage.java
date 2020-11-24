package entities;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SendMessage {

    private String clientName;
    private String type;
    private Map<String, Object> msg;

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

    public Map<String, Object> getMsg() {
        return msg;
    }

    public void setMsg(Map<String, Object> msg) {
        this.msg = msg;
    }

    public static String replay(String name, String type, String tip) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setClientName(name);
        sendMessage.setType(type);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tip", tip);
        sendMessage.setMsg(map);
        return new Gson().toJson(sendMessage);
    }
}
