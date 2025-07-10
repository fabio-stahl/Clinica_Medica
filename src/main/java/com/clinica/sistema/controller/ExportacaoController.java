package com.clinica.sistema.controller;

import com.clinica.sistema.model.Avaliacao;
import com.clinica.sistema.model.Consulta;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.repository.AvaliacaoRepository;
import com.clinica.sistema.repository.ConsultaRepository;
import com.clinica.sistema.repository.MedicoRepository;
import com.clinica.sistema.repository.PacienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/exportar")
public class ExportacaoController {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public ExportacaoController(ConsultaRepository consultaRepository,
                                MedicoRepository medicoRepository,
                                PacienteRepository pacienteRepository,
                                AvaliacaoRepository avaliacaoRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @PostMapping("/salvar/consultas")
    public ResponseEntity<String> salvarConsultasCSV() {
        List<Consulta> consultas = consultaRepository.findAll();

        try {
            File dir = new File("dados_csv");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, "consultas.csv");
            PrintWriter writer = new PrintWriter(new FileWriter(file));

            writer.println("ID,Medico,Paciente,Data,Status,Descricao");

            for (Consulta c : consultas) {
                writer.printf("%d,%s,%s,%s,%s,%s%n",
                        c.getId(),
                        c.getMedico().getNome(),
                        c.getPaciente().getNome(),
                        c.getData(),
                        c.getStatus(),
                        c.getDescricao() != null ? c.getDescricao().replace(",", " ") : "");
            }

            writer.flush();
            writer.close();

            return ResponseEntity.ok("Arquivo consultas.csv salvo com sucesso na pasta dados_csv.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao salvar CSV: " + e.getMessage());
        }
    }

    @PostMapping("/salvar/medicos")
    public ResponseEntity<String> salvarMedicosCSV() {
        List<Medico> medicos = medicoRepository.findAll();

        try {
            File dir = new File("dados_csv");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, "medicos.csv");
            PrintWriter writer = new PrintWriter(new FileWriter(file));

            writer.println("ID,Nome,Especialidade,PlanoDeSaude");

            for (Medico m : medicos) {
                writer.printf("%d,%s,%s,%s%n",
                        m.getId(),
                        m.getNome(),
                        m.getEspecialidade() != null ? m.getEspecialidade().getDescricao() : "",
                        m.getPlanoDeSaude());
            }

            writer.flush();
            writer.close();

            return ResponseEntity.ok("Arquivo medicos.csv salvo com sucesso na pasta dados_csv.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao salvar CSV: " + e.getMessage());
        }
    }

    @PostMapping("/salvar/pacientes")
    public ResponseEntity<String> salvarPacientesCSV() {
        List<Paciente> pacientes = pacienteRepository.findAll();

        try {
            File dir = new File("dados_csv");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, "pacientes.csv");
            PrintWriter writer = new PrintWriter(new FileWriter(file));

            writer.println("ID,Nome,Idade,PlanoDeSaude");

            for (Paciente p : pacientes) {
                writer.printf("%d,%s,%d,%s%n",
                        p.getId(),
                        p.getNome(),
                        p.getIdade(),
                        p.getPlanoDeSaude());
            }

            writer.flush();
            writer.close();

            return ResponseEntity.ok("Arquivo pacientes.csv salvo com sucesso na pasta dados_csv.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao salvar CSV: " + e.getMessage());
        }
    }

    @PostMapping("/salvar/avaliacoes")
    public ResponseEntity<String> salvarAvaliacoesCSV() {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAll();

        try {
            File dir = new File("dados_csv");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, "avaliacoes.csv");
            PrintWriter writer = new PrintWriter(new FileWriter(file));

            writer.println("ID,Paciente,Medico,Nota,Comentario");

            for (Avaliacao a : avaliacoes) {
                writer.printf("%d,%s,%s,%d,%s%n",
                        a.getId(),
                        a.getPaciente().getNome(),
                        a.getMedico().getNome(),
                        a.getNota(),
                        a.getComentario() != null ? a.getComentario().replace(",", " ") : "");
            }

            writer.flush();
            writer.close();

            return ResponseEntity.ok("Arquivo avaliacoes.csv salvo com sucesso na pasta dados_csv.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao salvar CSV: " + e.getMessage());
        }
    }
}
