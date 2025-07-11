package com.clinica.sistema.controller;

import com.clinica.sistema.model.*;
import com.clinica.sistema.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/importar")
public class ImportacaoController {

    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final ConsultaRepository consultaRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public ImportacaoController(PacienteRepository pacienteRepository,
                                MedicoRepository medicoRepository,
                                ConsultaRepository consultaRepository,
                                AvaliacaoRepository avaliacaoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.consultaRepository = consultaRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @PostMapping
    public ResponseEntity<String> importarArquivos(@RequestParam("files") MultipartFile[] files) {
        try {
            Map<Long, Paciente> pacientesCache = new HashMap<>();
            Map<Long, Medico> medicosCache = new HashMap<>();

            for (MultipartFile file : files) {
                String nomeArquivo = file.getOriginalFilename();

                if (nomeArquivo == null) continue;

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                String linha = reader.readLine(); // Pula o cabeçalho

                while ((linha = reader.readLine()) != null) {
                    String[] campos = linha.split(",");

                    switch (nomeArquivo) {

                        case "pacientes.csv" -> {
                            if (campos.length < 5) continue; // Atualizado para esperar 5 campos
                            Paciente paciente = new Paciente();
                            paciente.setId(Long.parseLong(campos[0]));
                            paciente.setNome(campos[1]);
                            paciente.setIdade(Integer.parseInt(campos[2]));
                            paciente.setPlanoDeSaude(campos[3]);
                            paciente.setSenha(campos[4]); // Importa a senha
                            pacienteRepository.save(paciente);
                            pacientesCache.put(paciente.getId(), paciente);
                        }


                        case "medicos.csv" -> {
                            if (campos.length < 5) continue; // Atualizado para esperar 5 campos
                            Medico medico = new Medico();
                            medico.setId(Long.parseLong(campos[0]));
                            medico.setNome(campos[1]);
                            medico.setEspecialidade(Especialidade.valueOfDescricao(campos[2]));
                            medico.setPlanoDeSaude(campos[3]);
                            medico.setSenha(campos[4]); // Importa a senha
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

                reader.close();
            }

            return ResponseEntity.ok("Importação concluída com sucesso.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Erro ao importar arquivos: " + e.getMessage());
        }
    }
}
