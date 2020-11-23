package com.server.mp.server.config.security;

import com.google.gson.Gson;
import com.server.mp.server.entities.response.BaseResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginFailHandler implements AuthenticationFailureHandler {
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        // JSON 信息
//        Map<String, Object> map = new HashMap<String, Object>(3);

        BaseResponse baseResponse = new BaseResponse();
//        map.put("code", 401);
        if (exception instanceof LockedException) {
            baseResponse.setReturn("9997");
        } else if (exception instanceof CredentialsExpiredException) {
            baseResponse.setReturn("9996");
        } else if (exception instanceof AccountExpiredException) {
            baseResponse.setReturn("9992");
        } else if (exception instanceof DisabledException) {
            baseResponse.setReturn("9995");
        } else if (exception instanceof BadCredentialsException) {
            baseResponse.setReturn("9993");
        }
        // 将 JSON 信息写入响应
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
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