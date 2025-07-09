package com.clinica.sistema.service;

//package clinica.service;

import org.springframework.stereotype.Service;

import com.clinica.sistema.model.Pessoa;
import com.clinica.sistema.repository.MedicoRepository;
import com.clinica.sistema.repository.PacienteRepository;


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
