package com.clinica.sistema.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinica.sistema.exception.RecursoNaoEncontradoException;
import com.clinica.sistema.model.Consulta;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.repository.ConsultaRepository;
import com.clinica.sistema.repository.MedicoRepository;
import com.clinica.sistema.repository.PacienteRepository;
import com.clinica.sistema.service.ConsultaService;
import com.clinica.sistema.service.PacienteService;

@RestController
@RequestMapping("/consultas")
public class TelaConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private ConsultaService consultaService;

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
        Consulta consulta = consultaRepository.findById(dto.getIdConsulta()).orElse(null);
        if (consulta == null) {
            return ResponseEntity.badRequest().body("Consulta não encontrada.");
        }
        consulta.setDescricao(dto.getDescricao());
        consulta.setStatus(Consulta.StatusConsulta.REALIZADA);
        consultaRepository.save(consulta);

        Paciente paciente = consulta.getPaciente();
        double valorConsulta = 0.0;
        if (paciente.getPlanoDeSaude() == null || paciente.getPlanoDeSaude().isBlank() || paciente.getPlanoDeSaude().equalsIgnoreCase("não tenho")) {
            valorConsulta = calcularValorConsulta(consulta.getMedico());
        }

        return ResponseEntity.ok(Map.of(
            "mensagem", "Consulta realizada com sucesso!",
            "valorConsulta", valorConsulta
        ));
    }

    // Atualize o DTO:
    public static class RealizarConsultaDTO {
        private Long idConsulta;
        private String descricao;
        // getters e setters
        public Long getIdConsulta() { return idConsulta; }
        public void setIdConsulta(Long idConsulta) { this.idConsulta = idConsulta; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
    }

    // Adicione este método utilitário no controller ou service
    private double calcularValorConsulta(Medico medico) {
        return switch (medico.getEspecialidade()) {
            case CARDIOLOGIA -> 300.0;
            case PEDIATRIA, GINECOLOGIA -> 200.0;
            case ORTOPEDIA, GERIATRIA -> 250.0;
            case UROLOGIA -> 400.0;
            case NEUROLOGIA -> 500.0;
            case PSIQUIATRIA -> 600.0;
            case DERMATOLOGIA -> 150.0;
            case OFTALMOLOGIA -> 270.0;
            case ENDOCRINOLOGIA -> 320.0;
            case GASTROENTEROLOGIA -> 420.0;
        };
    }
    @GetMapping("/consultas/nao-avaliadas/{pacienteId}")
    public ResponseEntity<List<Consulta>> consultasNaoAvaliadas(@PathVariable Long pacienteId) {
        Paciente paciente = pacienteService.buscarPorId(pacienteId);

        List<Consulta> realizadas = consultaService.listarPorPaciente(paciente).stream()
                .filter(c -> c.getStatus() == Consulta.StatusConsulta.REALIZADA)
                .filter(c -> !consultaService.foiAvaliada(c)) // Novo método
                .toList();

        return ResponseEntity.ok(realizadas);
    }
    @GetMapping("/por-medico")
    public List<Consulta> listarConsultasPorMedico(@RequestParam String nomeMedico) {
        Medico medico = medicoRepository.findByNome(nomeMedico);
        if (medico == null) return List.of();
        return consultaRepository.findAll().stream()
                .filter(c -> c.getMedico().equals(medico))
                .filter(c -> c.getStatus() == Consulta.StatusConsulta.AGENDADA)
                .toList();
    }

}
