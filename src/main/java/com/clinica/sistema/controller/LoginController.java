package com.clinica.sistema.controller;

import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/paciente")
    public ResponseEntity<?> loginPaciente(@RequestBody Paciente login) {
        Paciente paciente = (Paciente) loginService.autenticar(login.getNome(), login.getSenha());
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        } else {
            return ResponseEntity.status(401).body("Login inválido");
        }
    }

    @PostMapping("/medico")
    public ResponseEntity<?> loginMedico(@RequestBody Medico login) {
        Medico medico = (Medico) loginService.autenticar(login.getNome(), login.getSenha());
        if (medico != null) {
            return ResponseEntity.ok(medico);
        } else {
            return ResponseEntity.status(401).body("Login inválido");
        }
    }
}
