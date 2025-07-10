package com.clinica.sitema.controller;

import com.clinica.sitema.model.Medico;
import com.clinica.sitema.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class TelaMedicoController {

    @Autowired
    private MedicoService medicoService;

    // POST /medicos
    @PostMapping
    public ResponseEntity<String> cadastrarMedico(@RequestBody Medico medico) {
        boolean cadastrado = medicoService.cadastrarMedico(medico);

        if (cadastrado) {
            return ResponseEntity.ok("Médico cadastrado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Já existe um médico com esse nome.");
        }
    }

    // PUT /medicos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarMedico(@PathVariable Long id, @RequestBody Medico medicoAtualizado) {
        boolean atualizado = medicoService.alterarDados(id, medicoAtualizado);

        if (atualizado) {
            return ResponseEntity.ok("Médico atualizado com sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
