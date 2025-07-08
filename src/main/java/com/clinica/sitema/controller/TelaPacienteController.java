package com.clinica.sitema.controller;

import com.clinica.sitema.model.Consulta;
import com.clinica.sitema.service.ConsultaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class TelaPacienteController {

    private final ConsultaService consultaService;

    public TelaPacienteController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping("/consultas")
    public List<Consulta> listarConsultas(@RequestParam Long idPaciente) {
        // Busque Paciente pelo id e chame consultaService.listarPorPaciente(...)
        return null;
    }
}
