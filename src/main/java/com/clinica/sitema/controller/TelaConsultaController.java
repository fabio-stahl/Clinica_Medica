package com.clinica.sitema.controller;

import com.clinica.sitema.DTO.*;
import com.clinica.sitema.model.Consulta;
import com.clinica.sitema.model.Medico;
import com.clinica.sitema.model.Paciente;
import com.clinica.sitema.service.ConsultaService;
import com.clinica.sitema.service.MedicoService;
import com.clinica.sitema.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consulta")
public class TelaConsultaController {

    private final ConsultaService consultaService;
    private final MedicoService medicoService;
    private final PacienteService pacienteService;

    public TelaConsultaController(ConsultaService consultaService,
                                  MedicoService medicoService,
                                  PacienteService pacienteService) {
        this.consultaService = consultaService;
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
    }

    @PostMapping("/agendar")
    public ResponseEntity<?> agendarConsulta(@RequestBody AgendamentoConsultaDTO dto) {
        Medico medico = medicoService.buscarPorId(dto.getMedicoId());
        Paciente paciente = pacienteService.buscarPorId(dto.getPacienteId());

        consultaService.agendarConsulta(medico, paciente, dto.getData());
        return ResponseEntity.ok("Consulta agendada ou adicionada à lista de espera.");
    }

    @PostMapping("/cancelar")
    public ResponseEntity<?> cancelarConsulta(@RequestBody CancelarConsultaDTO dto) {
        Consulta consulta = consultaService.buscarPorId(dto.getConsultaId());

        if (consulta == null) {
            return ResponseEntity.badRequest().body("Consulta não encontrada.");
        }

        consultaService.cancelarConsulta(consulta);
        return ResponseEntity.ok("Consulta cancelada com sucesso.");
    }

    @PostMapping("/realizar")
    public ResponseEntity<?> realizarConsulta(@RequestBody RealizarConsultaDTo dto) {
        Consulta consulta = consultaService.buscarPorId(dto.getConsultaId());

        if (consulta == null) {
            return ResponseEntity.badRequest().body("Consulta não encontrada.");
        }

        double valor = consultaService.realizarConsulta(consulta, dto.getDescricao());

        if (valor > 0) {
            return ResponseEntity.ok("Consulta realizada. Valor a pagar: R$ " + valor);
        } else {
            return ResponseEntity.ok("Consulta realizada com plano de saúde.");
        }
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<Consulta>> listarPorPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscarPorId(id);
        return ResponseEntity.ok(consultaService.listarPorPaciente(paciente));
    }

    @GetMapping("/medico/{id}")
    public ResponseEntity<List<Consulta>> listarPorMedico(@PathVariable Long id) {
        Medico medico = medicoService.buscarPorId(id);
        return ResponseEntity.ok(consultaService.listarPorMedico(medico));
    }
}
