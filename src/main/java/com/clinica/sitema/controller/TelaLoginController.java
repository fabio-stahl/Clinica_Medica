package com.clinica.sitema.controller;

import com.clinica.sitema.DTO.LoginDTO;
import com.clinica.sitema.model.Medico;
import com.clinica.sitema.model.Paciente;
import com.clinica.sitema.model.Pessoa;
import com.clinica.sitema.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class TelaLoginController {

    private final LoginService loginService;

    public TelaLoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Pessoa pessoa = loginService.autenticar(loginDTO.getNome(), loginDTO.getSenha());

        if (pessoa != null) {
            String tipo;
            if (pessoa instanceof Medico) {
                tipo = "Medico";
            } else if (pessoa instanceof Paciente) {
                tipo = "Paciente";
            } else {
                tipo = "Desconhecido";
            }

            // Monta uma resposta simples
            return ResponseEntity.ok().body(
                    String.format("Login bem-sucedido! Usuário: %s, Tipo: %s", pessoa.getNome(), tipo)
            );
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nome ou senha inválidos.");
        }
    }
}
