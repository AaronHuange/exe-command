package com.server.mp.server.config.security;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class JsonLoginConfigurer extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        // 判断 ContentType 类型
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            // 获取请求内容
            Map<String, String> loginData = new HashMap<>(2);
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            try {
                inputStream = request.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                loginData = new Gson().fromJson(inputStreamReader, Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        if (inputStreamReader != null) {
                            inputStreamReader = null;
                        }
                    }
                }

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        if (inputStream != null) {
                            inputStream = null;
                        }
                    }
                }
            }
            String username = loginData.get(getUsernameParameter());
            String password = loginData.get(getPasswordParameter());
            // 创建 Authentication
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, authentication);
            // 执行身份验证
            return this.getAuthenticationManager().authenticate(authentication);
        } else {
            // 兼容表单登陆
            return super.attemptAuthentication(request, response);
        }
    }
}
