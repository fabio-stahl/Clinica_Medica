package com.clinica.sistema.controller;

import com.clinica.sistema.model.*;
import com.clinica.sistema.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
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
            File file = criarArquivo("consultas.csv");

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("ID,Medico,Paciente,Data,Status,Descricao");

                for (Consulta c : consultas) {
                    writer.printf("%d,%s,%s,%s,%s,%s%n",
                            c.getId(),
                            limpar(c.getMedico().getNome()),
                            limpar(c.getPaciente().getNome()),
                            c.getData(),
                            c.getStatus(),
                            c.getDescricao() != null ? limpar(c.getDescricao()) : "");
                }
            }

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
            File file = criarArquivo("medicos.csv");

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("ID,Nome,Especialidade,PlanoDeSaude,Senha");

                for (Medico m : medicos) {
                    writer.printf("%d,%s,%s,%s,%s%n",
                            m.getId(),
                            limpar(m.getNome()),
                            m.getEspecialidade() != null ? limpar(m.getEspecialidade().getDescricao()) : "",
                            limpar(m.getPlanoDeSaude()),
                            limpar(m.getSenha()));
                }
            }

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
            File file = criarArquivo("pacientes.csv");

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("ID,Nome,Idade,PlanoDeSaude,Senha");

                for (Paciente p : pacientes) {
                    writer.printf("%d,%s,%d,%s,%s%n",
                            p.getId(),
                            limpar(p.getNome()),
                            p.getIdade(),
                            limpar(p.getPlanoDeSaude()),
                            limpar(p.getSenha()));
                }
            }

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
            File file = criarArquivo("avaliacoes.csv");

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("ID,Paciente,Medico,Nota,Comentario");

                for (Avaliacao a : avaliacoes) {
                    writer.printf("%d,%s,%s,%d,%s%n",
                            a.getId(),
                            limpar(a.getPaciente().getNome()),
                            limpar(a.getMedico().getNome()),
                            a.getNota(),
                            a.getComentario() != null ? limpar(a.getComentario()) : "");
                }
            }

            return ResponseEntity.ok("Arquivo avaliacoes.csv salvo com sucesso na pasta dados_csv.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao salvar CSV: " + e.getMessage());
        }
    }


    private File criarArquivo(String nomeArquivo) throws IOException {
        File dir = new File("dados_csv");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, nomeArquivo);
    }


    private String limpar(String valor) {
        return valor != null ? valor.replace(",", " ") : "";
    }
}
