package com.clinica.sitema.service;


import com.clinica.sitema.model.Avaliacao;
import com.clinica.sitema.model.Medico;
import com.clinica.sitema.model.Paciente;
import com.clinica.sitema.repository.AvaliacaoRepository;
import com.clinica.sitema.repository.ConsultaRepository;
import com.clinica.sitema.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    public void avaliarConsulta(String mensagem, Medico medico, Paciente paciente, int nota){
        Avaliacao avaliacao = new Avaliacao(paciente, medico, nota, mensagem);
        medico.getAvaliacoes().add(avaliacao);
        avaliacaoRepository.save(avaliacao);
        medicoRepository.save(medico);

    }


}
