package com.server.mp.server.controller;

import com.server.mp.server.entities.response.LoginResponse;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class LoginController {

    @PostMapping("TEST")
    public LoginResponse logintest() {
        LoginResponse response = new LoginResponse();
        return response;
    }

}
