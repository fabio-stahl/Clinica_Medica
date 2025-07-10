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
        if (data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Não é possível agendar para datas passadas.");
        }


        boolean jaAgendado = consultas.findAll().stream()
                .anyMatch(c -> c.getMedico().equals(medico)
                        && c.getPaciente().equals(paciente)
                        && c.getData().equals(data)
                        && c.getStatus() == Consulta.StatusConsulta.AGENDADA);

        if (jaAgendado) {
            throw new IllegalStateException("Paciente já possui consulta com este médico nessa data.");
        }


        long count = consultas.findAll().stream()
                .filter(c -> c.getMedico().equals(medico)
                        && c.getData().equals(data)
                        && c.getStatus() == Consulta.StatusConsulta.AGENDADA)
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
        consultas.save(consulta);

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
        return switch (medico.getEspecialidade()) {
            case CARDIOLOGIA -> 300.0;
            case PEDIATRIA, GINECOLOGIA -> 200.0;
            case ORTOPEDIA, GERIATRIA -> 250.0;
            case UROLOGIA -> 400.0;
            case NEUROLOGIA -> 500.0;
            case PSIQUIATRIA -> 600.0;
            case DERMATOLOGIA -> 150.0;
            case OFTALMOLOGIA -> 270.0;
            case ENDOCRINOLOGIA -> 320.0;
            case GASTROENTEROLOGIA -> 420.0;
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
    public Consulta buscarPorId(Long id) {
        return consultas.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Consulta com ID " + id + " não encontrada."));
    }

}
