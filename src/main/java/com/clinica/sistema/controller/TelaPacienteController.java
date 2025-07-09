package com.clinica.sistema.controller;

import org.springframework.web.bind.annotation.*;

import com.clinica.sistema.model.Consulta;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.service.ConsultaService;
import com.clinica.sistema.service.MedicoService;
import com.clinica.sistema.service.PacienteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/consulta")
public class TelaPacienteController {
    private final ConsultaService consultaService;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;

    public TelaPacienteController(MedicoService medicoService, ConsultaService consultaService, PacienteService pacienteService) {
        this.medicoService = medicoService;
        this.consultaService = consultaService;
        this.pacienteService = pacienteService;
    }

    // Exemplo: GET /consulta?pacienteId=123
    @GetMapping
    public List<Consulta> listarConsultasDoPaciente(@RequestParam Long pacienteId) {
        Paciente paciente = pacienteService.buscarPorId(pacienteId);
        if (paciente == null) {
            throw new RuntimeException("Paciente não encontrado");
        }
        return consultaService.listarPorPaciente(paciente);
    }

    // Exemplo básico de agendar consulta via POST
    @PostMapping("/agendar")
    public String agendarConsulta(
            @RequestParam Long medicoId,
            @RequestParam Long pacienteId,
            @RequestParam String data) {
        // converte data String para LocalDate
        LocalDate localDate = LocalDate.parse(data);

        Medico medico = medicoService.buscarPorId(medicoId);
        Paciente paciente = pacienteService.buscarPorId(pacienteId);

        if (medico == null || paciente == null) {
            return "Médico ou Paciente não encontrado.";
        }

        try {
            consultaService.agendarConsulta(medico, paciente, localDate);
            return "Consulta agendada com sucesso.";
        } catch (Exception e) {
            return "Erro ao agendar consulta: " + e.getMessage();
        }
    }

    @GetMapping("/consultas")
    public List<Consulta> listarConsultas(@RequestParam Long idPaciente) {
        // Busque Paciente pelo id e chame consultaService.listarPorPaciente(...)
        return null;
    }
}
