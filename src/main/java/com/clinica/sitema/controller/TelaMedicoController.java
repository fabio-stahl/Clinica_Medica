package com.clinica.sitema.controller;


import com.clinica.sitema.service.ConsultaService;
import com.clinica.sitema.service.MedicoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Medico")
public class TelaMedicoController {
    private final MedicoService medicoService;

    public TelaMedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }
}
