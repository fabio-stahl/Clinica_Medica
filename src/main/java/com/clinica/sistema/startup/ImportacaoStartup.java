package com.clinica.sistema.startup;

import com.clinica.sistema.model.*;
import com.clinica.sistema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class ImportacaoStartup implements CommandLineRunner {
    @Autowired
    private final PacienteRepository pacienteRepository;
    @Autowired
    private final MedicoRepository medicoRepository;
    @Autowired
    private final ConsultaRepository consultaRepository;
    @Autowired
    private final AvaliacaoRepository avaliacaoRepository;

    public ImportacaoStartup(PacienteRepository pacienteRepository,
                             MedicoRepository medicoRepository,
                             ConsultaRepository consultaRepository,
                             AvaliacaoRepository avaliacaoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.consultaRepository = consultaRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @Override
    public void run(String... args) {
        importarDiretorio("dados");  // caminho relativo ao classpath
    }

    private void importarDiretorio(String nomeDiretorio) {
        try {
            Map<Long, Paciente> pacientesCache = new HashMap<>();
            Map<Long, Medico> medicosCache = new HashMap<>();

            String[] arquivos = {"pacientes.csv", "medicos.csv", "consultas.csv", "avaliacoes.csv"};

            for (String nomeArquivo : arquivos) {
                ClassPathResource resource = new ClassPathResource(nomeDiretorio + "/" + nomeArquivo);

                if (!resource.exists()) {
                    System.out.println("Arquivo não encontrado no classpath: " + nomeArquivo + ". Pulando...");
                    continue;  // pula para o próximo arquivo
                }

                try (InputStream inputStream = resource.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                    String linha = reader.readLine(); // cabeçalho

                    while ((linha = reader.readLine()) != null) {
                        String[] campos = linha.split(",");

                        switch (nomeArquivo) {
                            case "pacientes.csv" -> {
                                if (campos.length < 5) continue;
                                Paciente paciente = new Paciente();
                                paciente.setId(Long.parseLong(campos[0]));
                                paciente.setNome(campos[1]);
                                paciente.setIdade(Integer.parseInt(campos[2]));
                                paciente.setPlanoDeSaude(campos[3]);
                                paciente.setSenha(campos[4]);
                                pacienteRepository.save(paciente);
                                pacientesCache.put(paciente.getId(), paciente);
                            }

                            case "medicos.csv" -> {
                                if (campos.length < 5) continue;
                                Medico medico = new Medico();
                                medico.setId(Long.parseLong(campos[0]));
                                medico.setNome(campos[1]);
                                medico.setEspecialidade(Especialidade.valueOfDescricao(campos[2]));
                                medico.setPlanoDeSaude(campos[3]);
                                medico.setSenha(campos[4]);
                                medicoRepository.save(medico);
                                medicosCache.put(medico.getId(), medico);
                            }

                            case "consultas.csv" -> {
                                if (campos.length < 6) continue;
                                Consulta consulta = new Consulta();
                                consulta.setId(Long.parseLong(campos[0]));
                                Long idMedico = Long.parseLong(campos[1]);
                                Long idPaciente = Long.parseLong(campos[2]);
                                Medico medico = medicosCache.getOrDefault(idMedico,
                                        medicoRepository.findById(idMedico).orElse(null));
                                Paciente paciente = pacientesCache.getOrDefault(idPaciente,
                                        pacienteRepository.findById(idPaciente).orElse(null));
                                consulta.setMedico(medico);
                                consulta.setPaciente(paciente);
                                consulta.setData(LocalDate.parse(campos[3]).atStartOfDay());
                                consulta.setStatus(Consulta.StatusConsulta.valueOf(campos[4]));
                                consulta.setDescricao(campos[5]);
                                consultaRepository.save(consulta);
                            }

                            case "avaliacoes.csv" -> {
                                if (campos.length < 5) continue;
                                Avaliacao avaliacao = new Avaliacao();
                                avaliacao.setId(Long.parseLong(campos[0]));
                                Long idPaciente = Long.parseLong(campos[1]);
                                Long idMedico = Long.parseLong(campos[2]);
                                Paciente paciente = pacientesCache.getOrDefault(idPaciente,
                                        pacienteRepository.findById(idPaciente).orElse(null));
                                Medico medico = medicosCache.getOrDefault(idMedico,
                                        medicoRepository.findById(idMedico).orElse(null));
                                avaliacao.setPaciente(paciente);
                                avaliacao.setMedico(medico);
                                avaliacao.setNota(Integer.parseInt(campos[3]));
                                avaliacao.setComentario(campos[4]);
                                avaliacaoRepository.save(avaliacao);
                            }
                        }
                    }
                }
            }

            System.out.println("Importação automática concluída.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao importar arquivos automaticamente: " + e.getMessage());
        }
    }
}
