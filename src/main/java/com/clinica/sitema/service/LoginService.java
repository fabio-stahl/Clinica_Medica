package com.clinica.sitema.service;

//package clinica.service;

import com.clinica.sitema.model.Medico;
import com.clinica.sitema.model.Paciente;
import com.clinica.sitema.model.Pessoa;
import com.clinica.sitema.repository.MedicoRepository;
import com.clinica.sitema.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoginService {
    private MedicoRepository medicoRepository;
    private PacienteRepository pacienteRepository;

    public Pessoa autenticar(String nome, String senha) {
        // Primeiro, tenta achar como médico
        Pessoa p = medicoRepository.findByNome(nome);
        if (p != null && p.getSenha().equals(senha)) {
            return p;
        }

        // Se não achou ou a senha não bate, tenta como paciente
        Pessoa pp = pacienteRepository.findByNome(nome);
        if (pp != null && pp.getSenha().equals(senha)) {
            return pp;
        }

        // Não encontrado ou senha incorreta
        return null;
    }


}

