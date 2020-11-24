package com.server.mp.server.config.security;

import com.google.gson.Gson;
import com.server.mp.server.entities.response.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

//    private String adminname;

//    private String adminpassword;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //防止部署的时候还要安装数据库这里使用配置文件
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                // .authorities("")//这里只有一个管理员角色，不需要基于资源设置权限
                .roles("admin");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/command/exe/**")
                .hasRole("admin")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                //???.loginProcessingUrl("/command/exe/login")
                .and()
                .csrf()
                .disable();


        //json格式返回非法请求
        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = null;
                        try {
                            out = httpServletResponse.getWriter();
                            BaseResponse baseResponse = new BaseResponse();
                            baseResponse.setReturn("9994");
                            if (e instanceof InsufficientAuthenticationException) {
                                baseResponse.setReturn("9991");
                            }
                            out.write(new Gson().toJson(baseResponse));
                            out.flush();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            if (out != null) {
                                out.close();
                            }
                        }

                    }
                });
        //配置json登录接口
        JsonLoginConfigurer jsonLoginConfigurer = new JsonLoginConfigurer();
        jsonLoginConfigurer.setAuthenticationManager(authenticationManagerBean());
        jsonLoginConfigurer.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        jsonLoginConfigurer.setAuthenticationFailureHandler(new LoginFailHandler());
        http.addFilterAt(jsonLoginConfigurer, JsonLoginConfigurer.class);
    }
}
