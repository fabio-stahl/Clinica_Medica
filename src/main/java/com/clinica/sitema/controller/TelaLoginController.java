package com.clinica.sitema.controller;


import com.clinica.sitema.service.LoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Login")
public class TelaLoginController {

    private final LoginService loginService;

    public TelaLoginController(LoginService loginService) {
        this.loginService = loginService;
    }
}
