package com.clinica.sistema.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;  // ← import necessário

@Entity
@Table(name = "avaliacao_table")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Medico medico;

    private int nota;
    private String comentario;

    public Avaliacao() {}

    public Avaliacao(Paciente paciente, Medico medico, int nota, String comentario) {
        this.paciente = paciente;
        this.medico = medico;
        this.nota = nota;
        this.comentario = comentario;
    }

    public Long getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public int getNota() { return nota; }
    public String getComentario() { return comentario; }

    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setMedico(Medico medico)     { this.medico = medico; }
    public void setNota(int nota)            { this.nota = nota; }
    public void setComentario(String c)      { this.comentario = c; }
}
