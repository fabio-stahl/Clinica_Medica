package com.clinica.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinica.sistema.model.Medico;
import com.clinica.sistema.service.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    // POST /medicos
    @PostMapping
    public ResponseEntity<String> cadastrarMedico(@RequestBody Medico medico) {
        medicoService.cadastrarMedico(medico);
        return ResponseEntity.ok("Médico cadastrado com sucesso!");
    }

    // PUT /medicos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarMedico(@PathVariable Long id, @RequestBody Medico medicoAtualizado) {
        medicoService.alterarDados(id, medicoAtualizado);
        return ResponseEntity.ok("Médico atualizado com sucesso.");
    }
    
    @GetMapping
    public List<Medico> listar(@RequestParam(required = false) String nome,
                               @RequestParam(required = false) String especialidade) {
        // Chama o método do service para buscar médicos
        return medicoService.buscarMedicos(nome, especialidade);
    }
    
}
