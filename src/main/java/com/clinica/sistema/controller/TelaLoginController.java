package com.clinica.sistema.controller;

import com.clinica.sistema.model.Pessoa;
import com.clinica.sistema.service.LoginService;
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