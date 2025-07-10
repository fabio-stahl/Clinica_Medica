package com.clinica.sistema.controller;

import com.clinica.sistema.model.Avaliacao;
import com.clinica.sistema.model.Consulta;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.repository.AvaliacaoRepository;
import com.clinica.sistema.repository.ConsultaRepository;
import com.clinica.sistema.repository.MedicoRepository;
import com.clinica.sistema.repository.PacienteRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/consultas")
    public void exportarConsultas(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"consultas.csv\"");

        List<Consulta> consultas = consultaRepository.findAll();

        PrintWriter writer = response.getWriter();
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
    }

    @GetMapping("/medicos")
    public void exportarMedicos(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"medicos.csv\"");

        List<Medico> medicos = medicoRepository.findAll();

        PrintWriter writer = response.getWriter();
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
    }

    @GetMapping("/pacientes")
    public void exportarPacientes(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"pacientes.csv\"");

        List<Paciente> pacientes = pacienteRepository.findAll();

        PrintWriter writer = response.getWriter();
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
    }

    @GetMapping("/avaliacoes")
    public void exportarAvaliacoes(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"avaliacoes.csv\"");

        List<Avaliacao> avaliacoes = avaliacaoRepository.findAll();

        PrintWriter writer = response.getWriter();
        writer.println("ID,Paciente,Medico,Nota,Comentario");

        for (Avaliacao a : avaliacoes) {
            writer.printf("%d,%s,%s,%d,%s%n",
                    a.getId(),
                    a.getPaciente().getNome(),
                    a.getMedico().getNome(),
                    a.getNota(),
                    a.getComentario().replace(",", " "));
        }

        writer.flush();
        writer.close();
    }
}