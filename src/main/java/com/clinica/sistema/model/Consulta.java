package com.clinica.sistema.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Medico medico;

    @ManyToOne
    private Paciente paciente;
    
    private LocalDateTime data;
    private String descricao;
    private StatusConsulta status;

    public Consulta() {
        // Construtor padrão necessário para JPA
    }

    public Consulta(Paciente paciente, Medico medico, LocalDateTime data2) {
        this.medico = medico;
        this.paciente = paciente;
        this.data = data2;
        this.status = StatusConsulta.AGENDADA;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void cancelar() {
        this.status = StatusConsulta.CANCELADA;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Object getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setStatus(StatusConsulta statusConsulta) {
        this.status = statusConsulta;
    }

    public enum StatusConsulta {
        AGENDADA, CANCELADA, REALIZADA
    }

    public StatusConsulta getStatus(){
        return status;
    }

    
    // getters e setters
}
