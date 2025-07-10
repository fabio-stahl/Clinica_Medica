package com.clinica.sistema.controller;

import com.clinica.sistema.exception.RecursoNaoEncontradoException;
import com.clinica.sistema.model.Consulta;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.repository.MedicoRepository;
import com.clinica.sistema.repository.PacienteRepository;
import com.clinica.sistema.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/consultas")
public class TelaConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping("/agendar")
    public ResponseEntity<String> agendarConsulta(@RequestBody AgendamentoDTO dto) {
        Paciente paciente = pacienteRepository.findByNome(dto.getNomePaciente());
        if (paciente == null) throw new RecursoNaoEncontradoException("Paciente não encontrado.");

        Medico medico = medicoRepository.findByNome(dto.getNomeMedico());
        if (medico == null) throw new RecursoNaoEncontradoException("Médico não encontrado.");

        Consulta consulta = new Consulta(paciente, medico, LocalDateTime.parse(dto.getDataHora()));
        consulta.setDescricao(dto.getMotivo());
        consulta.setStatus(Consulta.StatusConsulta.AGENDADA);

        consultaRepository.save(consulta);

        return ResponseEntity.ok("Consulta agendada com sucesso!");
    }

    // DTO interno para receber os dados do frontend
    public static class AgendamentoDTO {
        private String nomePaciente;
        private String nomeMedico;
        private String dataHora;
        private String motivo;

        // getters e setters
        public String getNomePaciente() { return nomePaciente; }
        public void setNomePaciente(String nomePaciente) { this.nomePaciente = nomePaciente; }
        public String getNomeMedico() { return nomeMedico; }
        public void setNomeMedico(String nomeMedico) { this.nomeMedico = nomeMedico; }
        public String getDataHora() { return dataHora; }
        public void setDataHora(String dataHora) { this.dataHora = dataHora; }
        public String getMotivo() { return motivo; }
        public void setMotivo(String motivo) { this.motivo = motivo; }
    }

    @PostMapping("/realizar")
    public ResponseEntity<?> realizarConsulta(@RequestBody RealizarConsultaDTO dto) {
        Medico medico = medicoRepository.findByNome(dto.getNomeMedico());
        if (medico == null) throw new RecursoNaoEncontradoException("Médico não encontrado.");

        Paciente paciente = pacienteRepository.findByNome(dto.getNomePaciente());
        if (paciente == null) throw new RecursoNaoEncontradoException("Paciente não encontrado.");

        // Busca consulta agendada para esse médico, paciente e data
        Consulta consulta = consultaRepository.findAll().stream()
            .filter(c -> c.getMedico().equals(medico)
                    && c.getPaciente().equals(paciente)
                    && c.getData().toString().equals(dto.getDataHora())
                    && c.getStatus() == Consulta.StatusConsulta.AGENDADA)
            .findFirst()
            .orElse(null);

        if (consulta == null) {
            return ResponseEntity.badRequest().body("Consulta não encontrada.");
        }

        consulta.setDescricao(dto.getDescricao());
        consulta.setStatus(Consulta.StatusConsulta.REALIZADA);
        consultaRepository.save(consulta);

        // Calcule valor se necessário (exemplo: se paciente não tem plano)
        double valorConsulta = 0.0;
        if (paciente.getPlanoDeSaude() == null || paciente.getPlanoDeSaude().isBlank() || paciente.getPlanoDeSaude().equalsIgnoreCase("não tenho")) {
            valorConsulta = calcularValorConsulta(medico);
        }

        return ResponseEntity.ok(Map.of(
            "mensagem", "Consulta realizada com sucesso!",
            "valorConsulta", valorConsulta
        ));
    }

    // DTO para receber dados do frontend
    public static class RealizarConsultaDTO {
        private String nomeMedico;
        private String nomePaciente;
        private String dataHora;
        private String descricao;
        // getters e setters
        public String getNomeMedico() { return nomeMedico; }
        public void setNomeMedico(String nomeMedico) { this.nomeMedico = nomeMedico; }
        public String getNomePaciente() { return nomePaciente; }
        public void setNomePaciente(String nomePaciente) { this.nomePaciente = nomePaciente; }
        public String getDataHora() { return dataHora; }
        public void setDataHora(String dataHora) { this.dataHora = dataHora; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
    }

    // Adicione este método utilitário no controller ou service
    private double calcularValorConsulta(Medico medico) {
        return switch (medico.getEspecialidade().toLowerCase()) {
            case "cardiologia" -> 300.0;
            case "pediatria" -> 200.0;
            case "ortopedia" -> 250.0;
            default -> 180.0;
        };
    }
}
