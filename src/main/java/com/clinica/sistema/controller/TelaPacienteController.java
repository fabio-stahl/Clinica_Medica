package com.clinica.sistema.controller;

import com.clinica.sistema.exception.RecursoNaoEncontradoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clinica.sistema.model.Consulta;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.service.ConsultaService;
import com.clinica.sistema.service.MedicoService;
import com.clinica.sistema.service.PacienteService;

import java.time.LocalDateTime;
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
            throw new RecursoNaoEncontradoException("Paciente não encontrado.");
        }
        return consultaService.listarPorPaciente(paciente);
    }

    // Exemplo básico de agendar consulta via POST

    @PostMapping("/agendar")
    public ResponseEntity<String> agendarConsulta(
            @RequestParam Long medicoId,
            @RequestParam Long pacienteId,
            @RequestParam String data) {

        LocalDateTime localDate = LocalDateTime.parse(data);
        Medico medico = medicoService.buscarPorId(medicoId);
        Paciente paciente = pacienteService.buscarPorId(pacienteId);

        if (medico == null || paciente == null) {
            throw new RecursoNaoEncontradoException("Médico ou paciente não encontrado.");
        }

        consultaService.agendarConsulta(medico, paciente, localDate);
        return ResponseEntity.ok("Consulta agendada com sucesso.");
    }

    @GetMapping("/consultas")
    public List<Consulta> listarConsultas(@RequestParam Long idPaciente) {
        // Busque Paciente pelo id e chame consultaService.listarPorPaciente(...)
        return null;
    }
}
