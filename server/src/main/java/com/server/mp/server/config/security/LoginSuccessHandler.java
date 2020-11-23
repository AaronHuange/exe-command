package com.server.mp.server.config.security;

import com.google.gson.Gson;
import com.server.mp.server.entities.response.BaseResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setReturn("登陆成功");
        // 将 JSON 信息写入响应
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        //authentication.getPrincipal().toString()
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(new Gson().toJson(baseResponse));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}