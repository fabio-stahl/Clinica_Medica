package com.clinica.sistema.service;

import com.clinica.sistema.model.Avaliacao;
import com.clinica.sistema.model.Consulta;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.Paciente;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportacaoService {

    private final File dir = new File("src/main/resources/dados");

    public ExportacaoService() {
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void salvarPacienteNoCSV(Paciente paciente) {
        File file = new File(dir, "pacientes.csv");
        boolean novoArquivo = !file.exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (novoArquivo) {
                writer.println("ID,Nome,Idade,PlanoDeSaude,Senha");
            }

            writer.printf("%d,%s,%d,%s,%s%n",
                    paciente.getId(),
                    limpar(paciente.getNome()),
                    paciente.getIdade(),
                    limpar(paciente.getPlanoDeSaude()),
                    limpar(paciente.getSenha()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void salvarMedicoNoCSV(Medico medico) {
        File file = new File(dir, "medicos.csv");
        boolean novoArquivo = !file.exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (novoArquivo) {
                writer.println("ID,Nome,Especialidade,PlanoDeSaude,Senha");
            }

            writer.printf("%d,%s,%s,%s,%s%n",
                    medico.getId(),
                    limpar(medico.getNome()),
                    medico.getEspecialidade() != null ? limpar(medico.getEspecialidade().getDescricao()) : "",
                    limpar(medico.getPlanoDeSaude()),
                    limpar(medico.getSenha()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void salvarConsultaNoCSV(Consulta consulta) {
        File file = new File(dir, "consultas.csv");
        boolean novoArquivo = !file.exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (novoArquivo) {
                writer.println("ID,Medico,Paciente,Data,Status,Descricao");
            }

            writer.printf("%d,%s,%s,%s,%s,%s%n",
                    consulta.getId(),
                    limpar(consulta.getMedico().getNome()),
                    limpar(consulta.getPaciente().getNome()),
                    consulta.getData(),
                    consulta.getStatus(),
                    consulta.getDescricao() != null ? limpar(consulta.getDescricao()) : "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarAvaliacaoNoCSV(Avaliacao avaliacao) {
        File file = new File(dir, "avaliacoes.csv");
        boolean novoArquivo = !file.exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (novoArquivo) {
                writer.println("ID,Paciente,Medico,Nota,Comentario");
            }

            writer.printf("%d,%s,%s,%d,%s%n",
                    avaliacao.getId(),
                    limpar(avaliacao.getPaciente().getNome()),
                    limpar(avaliacao.getMedico().getNome()),
                    avaliacao.getNota(),
                    avaliacao.getComentario() != null ? limpar(avaliacao.getComentario()) : "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para remover vírgulas e evitar quebra de CSV
    private String limpar(String valor) {
        return valor != null ? valor.replace(",", " ") : "";
    }
    public void reescreverMedicosCSVComMedia(List<Medico> medicos) {
        File file = new File(dir, "medicos.csv");

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("ID,Nome,Especialidade,PlanoDeSaude,MediaNotas");

            for (Medico medico : medicos) {
                writer.printf("%d,%s,%s,%s,%.2f%n",
                        medico.getId(),
                        limpar(medico.getNome()),
                        medico.getEspecialidade() != null ? limpar(medico.getEspecialidade().getDescricao()) : "",
                        limpar(medico.getPlanoDeSaude()),
                        medico.getMediaEstrelas()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
