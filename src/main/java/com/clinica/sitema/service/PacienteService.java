package com.clinica.sitema.service;

import com.clinica.sitema.model.Consulta;
import com.clinica.sitema.model.Medico;
import com.clinica.sitema.model.Paciente;
import com.clinica.sitema.repository.MedicoRepository;
import com.clinica.sitema.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        boolean pacienteSemPlano = paciente.getPlanoDeSaude() == null ||
                paciente.getPlanoDeSaude().isEmpty() ||
                paciente.getPlanoDeSaude().equalsIgnoreCase("Não tenho");

        for (Medico medico : medicoRepository.findAll()) {
            boolean especialidadeConfere = medico.getEspecialidade().equalsIgnoreCase(especialidade);
            boolean nomeConfere = medico.getNome().equalsIgnoreCase(nome);

            boolean planoConfere = pacienteSemPlano ||
                    paciente.getPlanoDeSaude().equalsIgnoreCase(medico.getPlanoDeSaude());

            if (especialidadeConfere && planoConfere && nomeConfere) {
                medicosEncontrados.add(medico);
            }
        }

        return medicosEncontrados;
    }



    public Paciente buscarPorId(Long pacienteId) {
        return (Paciente) pacienteRepository.findById(pacienteId).orElse(null);
    }
}
