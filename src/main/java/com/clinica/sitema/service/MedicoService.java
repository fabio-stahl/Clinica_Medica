package com.clinica.sitema.service;

import com.clinica.sitema.model.Medico;
import com.clinica.sitema.repository.MedicoRepository;
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
        return medicoRepository.findById(medicoId).orElse(null);
    }
}
