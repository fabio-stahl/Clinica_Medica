package com.clinica.sistema.service;

import com.clinica.sistema.exception.ConsultaDuplicadaException;
import com.clinica.sistema.model.Consulta;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.repository.ConsultaRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ConsultaService {

    private final ConsultaRepository consultas;
    private final Map<Medico, Map<LocalDateTime, Queue<Paciente>>> listaEspera = new HashMap<>();

    public ConsultaService(ConsultaRepository consultas) {
        this.consultas = consultas;
    }

    public void agendarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        if (data.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar para datas passadas.");
        }

        // Verifica se paciente já tem consulta com o médico na data
        boolean jaAgendado = consultas.findAll().stream()
                .anyMatch(c -> c.getMedico().equals(medico)
                        && c.getPaciente().equals(paciente)
                        && c.getData().equals(data)
                        && c.getStatus() == Consulta.StatusConsulta.AGENDADA);

        if (jaAgendado) {
            throw new ConsultaDuplicadaException("Paciente já possui consulta com este médico nessa data.");
        }

        // Conta consultas ativas no dia
        long count = consultas.findAll().stream()
                .filter(c -> c.getMedico().equals(medico)
                        && c.getData().equals(data)
                        && c.getStatus() == Consulta.StatusConsulta.AGENDADA)
                .count();

        if (count < 3) {
            Consulta novaConsulta = new Consulta(paciente, medico, data);
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
        consultas.save(consulta);

        Medico medico = consulta.getMedico();
        LocalDateTime data = consulta.getData();

        Queue<Paciente> fila = listaEspera
                .getOrDefault(medico, Collections.emptyMap())
                .get(data);

        if (fila != null && !fila.isEmpty()) {
            Paciente proximo = fila.poll();
            Consulta nova = new Consulta(proximo, medico, data);
            consultas.save(nova);
        }
    }

    public double realizarConsulta(Consulta consulta, String descricao) {
        consulta.setDescricao(descricao);
        consulta.setStatus(Consulta.StatusConsulta.REALIZADA);
        consultas.save(consulta);

        Paciente paciente = (Paciente) consulta.getPaciente();

        if (paciente.getPlanoDeSaude() == null || paciente.getPlanoDeSaude().isBlank() || paciente.getPlanoDeSaude().equalsIgnoreCase("não tenho")) {
            double valor = calcularValorConsulta(consulta.getMedico());
            // Retorna o valor para o frontend exibir
            return valor;
        }
        return 0.0; // paciente com plano não paga
    }


    private double calcularValorConsulta(Medico medico) {
        return switch (medico.getEspecialidade().toLowerCase()) {
            case "cardiologia" -> 300.0;
            case "pediatria" -> 200.0;
            case "ortopedia" -> 250.0;
            default -> 180.0;
        };
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
