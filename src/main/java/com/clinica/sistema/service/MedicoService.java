package com.clinica.sistema.service;

import com.clinica.sistema.model.Medico;
import com.clinica.sistema.repository.MedicoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public boolean cadastrarMedico(Medico medico) {
        if (medicoRepository.existsByNome(medico.getNome())) {
            return false;
        }
        medicoRepository.save(medico);
        return true;
    }

    public boolean alterarDados(Long id, Medico medicoAtualizado) {
        if (medicoRepository.existsById(id)) {
            Medico medico = medicoRepository.findById(id).get();
            medico.setNome(medicoAtualizado.getNome());
            medico.setEspecialidade(medicoAtualizado.getEspecialidade());
            medico.setPlanoDeSaude(medicoAtualizado.getPlanoDeSaude());
            // Outros campos, se houver
            medicoRepository.save(medico);
            return true;
        }
        return false;
    }

    public Medico buscarPorId(Long medicoId) {
        return (Medico) medicoRepository.findById(medicoId).orElse(null);
    }

    public List<Medico> buscarMedicos(String nome, String especialidade) {
        if ((nome == null || nome.isEmpty()) && (especialidade == null || especialidade.isEmpty())) {
            return medicoRepository.findAll();
        } else if (nome != null && !nome.isEmpty() && (especialidade == null || especialidade.isEmpty())) {
            return medicoRepository.findByNomeContainingIgnoreCase(nome);
        } else if ((nome == null || nome.isEmpty()) && especialidade != null && !especialidade.isEmpty()) {
            return medicoRepository.findByEspecialidadeContainingIgnoreCase(especialidade);
        } else {
            return medicoRepository.findByNomeContainingIgnoreCaseAndEspecialidadeContainingIgnoreCase(nome, especialidade);
        }
    }
}
