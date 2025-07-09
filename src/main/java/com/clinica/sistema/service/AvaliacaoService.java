package com.clinica.sistema.service;


import com.clinica.sistema.model.Avaliacao;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.repository.AvaliacaoRepository;
import com.clinica.sistema.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {
    @Autowired
    private ConsultaRepository consultaRepository;
    private AvaliacaoRepository avaliacaoRepository;

    public void avaliarConsulta(String mensagem, Medico medico, Paciente paciente, int nota){
        Avaliacao avaliacao = new Avaliacao(paciente, medico, nota, mensagem);
        medico.getAvaliacoes().add(avaliacao);
        avaliacaoRepository.save(avaliacao);

    }


}
