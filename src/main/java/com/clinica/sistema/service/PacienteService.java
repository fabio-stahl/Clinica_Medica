package com.clinica.sistema.service;

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

    public boolean cadastrarPaciente(Paciente paciente){
        if (pacienteRepository.existsByNome(paciente.getNome())) {
            return false;
        }
        pacienteRepository.save(paciente);
        return true;
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
        return pacienteRepository.findById(pacienteId).orElse(null);
    }
}
