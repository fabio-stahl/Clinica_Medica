package com.clinica.sitema.controller;


import com.clinica.sitema.model.Consulta;
import com.clinica.sitema.service.ConsultaService;
import com.clinica.sitema.service.PacienteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Consulta")
public class TelaPacienteController {
    private final PacienteService pacienteService;

    public TelaPacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    @GetMapping
    public List<Consulta> getAll(){
        return pacienteService.getAll;
    }
    @PostMapping
    
}
