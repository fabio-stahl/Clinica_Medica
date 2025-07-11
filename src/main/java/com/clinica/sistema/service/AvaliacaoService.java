package com.clinica.sistema.service;


import com.clinica.sistema.model.Avaliacao;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.repository.AvaliacaoRepository;
import com.clinica.sistema.repository.ConsultaRepository;
import com.clinica.sistema.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AvaliacaoService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ExportacaoService exportacaoService;

    public void avaliarConsulta(String mensagem, Medico medico, Paciente paciente, int nota) {
        if (medico == null || paciente == null) {
            throw new IllegalArgumentException("Médico e paciente não podem ser nulos.");
        }

        Avaliacao avaliacao = new Avaliacao(paciente, medico, nota, mensagem);

        if (medico.getAvaliacoes() == null) {
            medico.setAvaliacoes(new ArrayList<>());
        }

        medico.getAvaliacoes().add(avaliacao);
        avaliacaoRepository.save(avaliacao);
        medicoRepository.save(medico);
        exportacaoService.salvarAvaliacaoNoCSV(avaliacao);

        // Atualiza todos os médicos no CSV com as médias recalculadas
        exportacaoService.reescreverMedicosCSVComMedia(medicoRepository.findAll());
    }



}
