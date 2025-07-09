package com.clinica.sistema.model;

import java.time.LocalDate;

public class Consulta {
    private final Medico medico;
    private final Paciente paciente;
    private LocalDate data;
    private String descricao;
    private StatusConsulta status;

    public Consulta(Medico medico, Paciente paciente, LocalDate data) {
        this.medico = medico;
        this.paciente = paciente;
        this.data = data;
        this.status = StatusConsulta.AGENDADA;
    }

    public LocalDate getData() {
        return data;
    }

    public Medico getMedico() {
        return medico;
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

    public void setStatus(StatusConsulta statusConsulta) {
        this.status = status;
    }

    public enum StatusConsulta {
        AGENDADA, CANCELADA, REALIZADA
    }

    public StatusConsulta getStatus(){
        return status;
    }
    // getters e setters
}
