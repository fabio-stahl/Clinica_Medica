package com.clinica.sitema.controller;

import com.clinica.sitema.service.ConsultaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/Consulta")
public class TelaConsultaController {
    private final ConsultaService consultaService;

    public TelaConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }
}
