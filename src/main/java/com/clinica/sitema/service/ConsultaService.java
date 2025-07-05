package com.clinica.sitema.service;

package clinica.service;

import com.clinica.sitema.model.Consulta;
import com.clinica.sitema.model.Medico;
import com.clinica.sitema.model.Paciente;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ConsultaService {
    private List<Consulta> consultas = new ArrayList<>();
    private Map<Medico, Map<LocalDate, Queue<Paciente>>> listaEspera = new HashMap<>();

    public boolean agendarConsulta(Paciente paciente, Medico medico, LocalDate data) {
        long count = consultas.stream()
                .filter(c -> c.getMedico().equals(medico) && c.getData().equals(data) && c.getStatus() == Consulta.StatusConsulta.AGENDADA)
                .count();

        if (count < 3) {
            consultas.add(new Consulta(medico, paciente, data));
            return true;
        } else {
            listaEspera.computeIfAbsent(medico, k -> new HashMap<>())
                    .computeIfAbsent(data, k -> new LinkedList<>())
                    .add(paciente);
            return false;
        }
    }

    public void cancelarConsulta(Consulta consulta) {
        consulta.cancelar();
        Medico medico = consulta.getMedico();
        LocalDate data = consulta.getData();

        Queue<Paciente> fila = listaEspera.getOrDefault(medico, Collections.emptyMap()).get(data);
        if (fila != null && !fila.isEmpty()) {
            Paciente proximo = fila.poll();
            consultas.add(new Consulta(medico, proximo, data));
        }
    }

    public void realizarConsulta(Consulta consulta, String descricao) {
        consulta.setDescricao(descricao);
    }

    public void agendarConsulta(Medico medico, Paciente paciente, LocalDate data) {
        Consulta novaConsulta = new Consulta(medico, paciente, data);
        consultas.add(novaConsulta);
    }

    public List<Consulta> listarPorPaciente(Paciente p) {
        return consultas.stream().filter(c -> c.getPaciente().equals(p)).toList();
    }

    public List<Consulta> listarPorMedico(Medico m) {
        return consultas.stream().filter(c -> c.getMedico().equals(m)).toList();
    }
}
