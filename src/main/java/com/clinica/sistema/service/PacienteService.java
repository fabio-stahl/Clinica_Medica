package com.clinica.sistema.service;

import com.clinica.sistema.exception.PacienteExistenteException;
import com.clinica.sistema.exception.PacienteNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica.sistema.model.Consulta;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.repository.MedicoRepository;
import com.clinica.sistema.repository.PacienteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ExportacaoService exportacaoService;

    public void cadastrarPaciente(Paciente paciente){
        if (pacienteRepository.existsByNome(paciente.getNome())) {
            throw new PacienteExistenteException("Já existe um paciente com esse nome.");
        }
        pacienteRepository.save(paciente);
        exportacaoService.salvarPacienteNoCSV(paciente);
    }

    public List<Medico> pesquisarMedicos(String nome, String especialidade, Paciente paciente) {
        List<Medico> medicosEncontrados = new ArrayList<>();

        for (Medico medico : medicoRepository.findAll()) {
            boolean nomeConfere = medico.getNome().equalsIgnoreCase(nome);
            boolean especialidadeConfere = medico.getEspecialidade().equalsIgnoreCase(especialidade);
            boolean planoConfere = paciente.getPlanoDeSaude() == null ||
                    paciente.getPlanoDeSaude().isEmpty() ||
                    paciente.getPlanoDeSaude().equalsIgnoreCase(medico.getPlanoDeSaude());

            if (nomeConfere && especialidadeConfere && planoConfere) {
                medicosEncontrados.add(medico);
            }
        }

        return medicosEncontrados;
    }

    public Paciente buscarPorId(Long pacienteId) {
        return pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente com ID " + pacienteId + " não encontrado."));
    }
}
