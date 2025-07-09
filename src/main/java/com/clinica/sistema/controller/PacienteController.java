package com.clinica.sistema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.service.PacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<String> cadastrarPaciente(@RequestBody Paciente paciente) {
        boolean cadastrado = pacienteService.cadastrarPaciente(paciente);
        if (cadastrado) {
            return ResponseEntity.ok("Paciente cadastrado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Já existe um paciente com esse nome.");
        }
    }
}
