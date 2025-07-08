package com.clinica.sitema.controller;

import com.clinica.sitema.model.Medico;
import com.clinica.sitema.service.MedicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class TelaMedicoController {

    private final MedicoService medicoService;

    public TelaMedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping
    public List<Medico> listarMedicos() {
        // Implemente no MedicoService
        return medicoService.listarTodos();
    }

    @GetMapping("/buscar")
    public List<Medico> buscarPorEspecialidadeEPlano(
            @RequestParam(required = false) String especialidade,
            @RequestParam(required = false) String plano) {
        // Implemente no MedicoService
        return medicoService.buscar(especialidade, plano);
    }
}
