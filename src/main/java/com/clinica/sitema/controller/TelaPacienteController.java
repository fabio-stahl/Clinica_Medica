package com.clinica.sitema.controller;

import com.clinica.sitema.model.Consulta;
import com.clinica.sitema.model.Medico;
import com.clinica.sitema.model.Paciente;
import com.clinica.sitema.service.ConsultaService;
import com.clinica.sitema.service.MedicoService;
import com.clinica.sitema.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/consulta")
public class TelaPacienteController {
    @Autowired
    private final MedicoService medicoService;

    private final ConsultaService consultaService;
    private final PacienteService pacienteService;

    @Autowired
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
    @GetMapping("/pesquisar")
    public List<Medico> pesquisarMedicos(
            @RequestParam String nome,
            @RequestParam String especialidade,
            @AuthenticationPrincipal Paciente pacienteLogado) {

        return medicoService.pesquisarMedicos(nome, especialidade, pacienteLogado);
    }


}
