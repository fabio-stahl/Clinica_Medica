package com.clinica.sitema.controller;

import com.clinica.sitema.model.Pessoa;
import com.clinica.sitema.service.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class TelaLoginController {

    private final LoginService loginService;

    public TelaLoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public Pessoa login(@RequestParam String nome, @RequestParam String senha) {
        return loginService.autenticar(nome, senha);
    }
}