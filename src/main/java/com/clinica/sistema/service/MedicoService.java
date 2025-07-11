package com.clinica.sistema.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica.sistema.exception.MedicoExistenteException;
import com.clinica.sistema.exception.MedicoNaoEncontradoException;
import com.clinica.sistema.model.Especialidade;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.repository.MedicoRepository;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ExportacaoService exportacaoService;

    public void cadastrarMedico(Medico medico) {
        if (medicoRepository.existsByNome(medico.getNome())) {
            throw new MedicoExistenteException("Já existe um médico com esse nome");
        }
        medicoRepository.save(medico);
        exportacaoService.salvarMedicoNoCSV(medico);
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
            Especialidade especialidadeEnum = Arrays.stream(Especialidade.values())
                    .filter(e -> e.getDescricao().equalsIgnoreCase(especialidade))
                    .findFirst()
                    .orElse(null);

            if (especialidadeEnum != null) {
                return medicoRepository.findByEspecialidade(especialidadeEnum);
            } else {
                // Se não encontrou o enum, retorna lista vazia
                return List.of();
            }
        } else {
            Especialidade especialidadeEnum = Arrays.stream(Especialidade.values())
                    .filter(e -> e.getDescricao().equalsIgnoreCase(especialidade))
                    .findFirst()
                    .orElse(null);

            if (especialidadeEnum != null) {
                return medicoRepository.findByNomeContainingIgnoreCaseAndEspecialidade(nome, especialidadeEnum);
            } else {
                return List.of();
            }
        }
    }
}