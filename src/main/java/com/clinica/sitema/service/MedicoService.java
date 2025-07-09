package com.clinica.sitema.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.clinica.sitema.model.Medico;

@Service
public class MedicoService {
    private List<Medico> medicos = new ArrayList<>();

    public List<Medico> listarTodos() {
        return medicos;
    }

    public List<Medico> buscar(String especialidade, String plano) {
        List<Medico> resultado = new ArrayList<>();
        for (Medico m : medicos) {
            boolean matchEspecialidade = (especialidade == null || especialidade.isEmpty() || m.getEspecialidade().equalsIgnoreCase(especialidade));
            boolean matchPlano = (plano == null || plano.isEmpty() || m.getPlanoDeSaude().equalsIgnoreCase(plano));
            if (matchEspecialidade && matchPlano) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    // Você pode adicionar métodos para adicionar médicos à lista, se necessário
}
