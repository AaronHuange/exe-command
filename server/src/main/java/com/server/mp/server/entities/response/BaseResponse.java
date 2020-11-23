package com.server.mp.server.entities.response;

import java.util.HashMap;
import java.util.Map;

public class BaseResponse {
    private String code = "0000";
    private String msg = "success";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setReturn(String code) {
        if (returnCode.containsKey(code)) {
            setCode(code);
            setMsg(returnCode.get(code));
        }
    }

    private static Map<String, String> returnCode = new HashMap<>();

    static {
        returnCode.put("9991", "没有权限访问");
        returnCode.put("9994", "接口不存在");
    }

}
