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
            // Verifica se o nome bate (ou ignora se o filtro estiver vazio)
            boolean nomeConfere = nome == null || nome.isEmpty() ||
                    (medico.getNome() != null && medico.getNome().equalsIgnoreCase(nome));

            // Verifica se a especialidade bate (ou ignora se o filtro estiver vazio)
            boolean especialidadeConfere = especialidade == null || especialidade.isEmpty() ||
                    (medico.getEspecialidade() != null && medico.getEspecialidade().equalsIgnoreCase(especialidade));

            // Verifica se o plano do médico bate com o do paciente ou se o paciente não tem plano
            boolean planoConfere = pacienteSemPlano ||
                    (medico.getPlanoDeSaude() != null &&
                            medico.getPlanoDeSaude().equalsIgnoreCase(paciente.getPlanoDeSaude()));

            if (nomeConfere && especialidadeConfere && planoConfere) {
                medicosEncontrados.add(medico);
            }
        }

        return medicosEncontrados;
    }




    public Paciente buscarPorId(Long pacienteId) {
        return (Paciente) pacienteRepository.findById(pacienteId).orElse(null);
    }
}
