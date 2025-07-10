package com.clinica.sistema.service;

import com.clinica.sistema.exception.MedicoExistenteException;
import com.clinica.sistema.exception.MedicoNaoEncontradoException;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.repository.MedicoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public void cadastrarMedico(Medico medico) {
        if (medicoRepository.existsByNome(medico.getNome())) {
            throw new MedicoExistenteException("Já existe um médico com esse nome");
        }
        medicoRepository.save(medico);
    }

    public void alterarDados(Long id, Medico medicoAtualizado) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new MedicoNaoEncontradoException("Médico com ID " + id + " não encontrado."));

        medico.setNome(medicoAtualizado.getNome());
        medico.setEspecialidade(medicoAtualizado.getEspecialidade());
        medico.setPlanoDeSaude(medicoAtualizado.getPlanoDeSaude());
        medicoRepository.save(medico);
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
