package com.clinica.sitema.controller;

import com.clinica.sitema.service.ConsultaService;
import com.clinica.sitema.service.ConsultaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class TelaConsultaController {
    private final ConsultaService consultaService;

    private final ConsultaService consultaService;

    public TelaConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping("/agendar")
    public void agendar(@RequestParam Long idMedico, @RequestParam Long idPaciente, @RequestParam String data) {
        // Busque Medico e Paciente pelo id e chame consultaService.agendarConsulta(...)
    }

    @PostMapping("/cancelar")
    public void cancelar(@RequestParam Long idConsulta) {
        // Busque Consulta pelo id e chame consultaService.cancelarConsulta(...)
    }

    @PostMapping("/realizar")
    public void realizar(@RequestParam Long idConsulta, @RequestParam String descricao) {
        // Busque Consulta pelo id e chame consultaService.realizarConsulta(...)
    }
    public TelaConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }
}
