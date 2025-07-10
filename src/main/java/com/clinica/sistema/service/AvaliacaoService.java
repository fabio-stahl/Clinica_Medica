package com.clinica.sistema.service;


import com.clinica.sistema.exception.RecursoNaoEncontradoException;
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
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public void avaliarConsulta(String mensagem, Medico medico, Paciente paciente, int nota){
        if (medico == null || paciente == null) {
            throw new RecursoNaoEncontradoException("Médico ou paciente não encontrado.");
        }
        if (nota < 0 || nota > 5) {
            throw new IllegalArgumentException("Nota da avaliação deve ser entre 0 e 5.");
        }

        Avaliacao avaliacao = new Avaliacao(paciente, medico, nota, mensagem);
        medico.getAvaliacoes().add(avaliacao);
        avaliacaoRepository.save(avaliacao);
    }
}
