package com.clinica.sistema.controller;

import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.service.AvaliacaoService;
import com.clinica.sistema.service.ConsultaService;
import com.clinica.sistema.service.MedicoService;
import com.clinica.sistema.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ConsultaService consultaService;

    public static class AvaliacaoDTO {
        public Long consultaId;
        public Long medicoId;
        public Long pacienteId;
        public int nota;
        public String mensagem;

        // getters and setters omitted for brevity
    }

    @PostMapping("/avaliar")
    public ResponseEntity<String> avaliarConsulta(@RequestBody AvaliacaoDTO dto) {
        try {
            Medico medico = medicoService.buscarPorId(dto.medicoId);
            Paciente paciente = pacienteService.buscarPorId(dto.pacienteId);

            if (medico == null || paciente == null) {
                return ResponseEntity.badRequest().body("Médico ou Paciente não encontrado.");
            }

            avaliacaoService.avaliarConsulta(dto.mensagem, medico, paciente, dto.nota);

            // Optionally, mark the consulta as avaliada in consultaService if needed

            return ResponseEntity.ok("Avaliação salva com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao salvar avaliação: " + e.getMessage());
        }
    }
}
