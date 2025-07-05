package com.clinica.sitema.service;

import com.clinica.sitema.model.Consulta;
import com.clinica.sitema.model.Medico;
import com.clinica.sitema.model.Paciente;
import com.clinica.sitema.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ConsultaService {

    private final ConsultaRepository consultas;
    private final Map<Medico, Map<LocalDate, Queue<Paciente>>> listaEspera = new HashMap<>();

    public ConsultaService(ConsultaRepository consultas) {
        this.consultas = consultas;
    }

    public void agendarConsulta(Medico medico, Paciente paciente, LocalDate data) {
        // Verifica se já existem 3 consultas para o médico nesse dia
        long count = consultas.findAll().stream()
                .map(c -> (Consulta) c)
                .filter(c -> c.getMedico().equals(medico) &&
                        c.getData().equals(data) &&
                        c.getStatus() == Consulta.StatusConsulta.AGENDADA)
                .count();

        if (count < 3) {
            Consulta novaConsulta = new Consulta(medico, paciente, data);
            consultas.save(novaConsulta);
        } else {
            listaEspera
                    .computeIfAbsent(medico, k -> new HashMap<>())
                    .computeIfAbsent(data, k -> new LinkedList<>())
                    .add(paciente);
        }
    }

    public void cancelarConsulta(Consulta consulta) {
        consulta.cancelar();
        consultas.save(consulta); // atualiza no banco

        Medico medico = consulta.getMedico();
        LocalDate data = consulta.getData();

        Queue<Paciente> fila = listaEspera
                .getOrDefault(medico, Collections.emptyMap())
                .get(data);

        if (fila != null && !fila.isEmpty()) {
            Paciente proximo = fila.poll();
            Consulta nova = new Consulta(medico, proximo, data);
            consultas.save(nova);
        }
    }

    public void realizarConsulta(Consulta consulta, String descricao) {
        consulta.setDescricao(descricao);
        consulta.setStatus(Consulta.StatusConsulta.REALIZADA);
        consultas.save(consulta);
    }

    public List<Consulta> listarPorPaciente(Paciente paciente) {
        return consultas.findAll().stream()
                .filter(c -> c.getPaciente().equals(paciente))
                .toList();
    }

    public List<Consulta> listarPorMedico(Medico medico) {
        return consultas.findAll().stream()
                .filter(c -> c.getMedico().equals(medico))
                .toList();
    }
}
