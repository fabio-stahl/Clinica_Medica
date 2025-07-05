package com.clinica.sitema.service;

package clinica.service;

import com.clinica.sitema.model.Medico;
import com.clinica.sitema.model.Paciente;
import com.clinica.sitema.model.Pessoa;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoginService {
    private List<Paciente> pacientes;
    private List<Medico> medicos;

    public LoginService(List<Paciente> pacientes, List<Medico> medicos) {
        this.pacientes = pacientes;
        this.medicos = medicos;
    }

    public Pessoa autenticar(String nome, String senha) {
        for (Paciente p : pacientes) {
            if (p.getNome().equals(nome) && p.getSenha().equals(senha)) return p;
        }
        for (Medico m : medicos) {
            if (m.getNome().equals(nome) && m.getSenha().equals(senha)) return m;
        }
        return null;
    }
}
