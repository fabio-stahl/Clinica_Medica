package com.clinica.sistema.service;

//package clinica.service;

import com.clinica.sistema.exception.AutenticacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica.sistema.model.Pessoa;
import com.clinica.sistema.repository.MedicoRepository;
import com.clinica.sistema.repository.PacienteRepository;


@Service
public class LoginService {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    public Pessoa autenticar(String nome, String senha) {
        // Primeiro, tenta achar como médico
        Pessoa p = medicoRepository.findByNome(nome);
        if (p != null && p.getSenha().equals(senha)) return p;

        // Se não achou ou a senha não bate, tenta como paciente
        p = pacienteRepository.findByNome(nome);
        if (p != null && p.getSenha().equals(senha)) return p;

        throw new AutenticacaoException("Nome ou senha inválidos.");
    }


}
