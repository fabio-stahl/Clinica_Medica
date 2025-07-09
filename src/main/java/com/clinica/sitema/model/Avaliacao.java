package com.clinica.sitema.model;

import com.clinica.sitema.model.Medico;
import com.clinica.sitema.model.Paciente;
import jakarta.persistence.*;

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

    public Avaliacao() {} // construtor vazio exigido pelo JPA

    public Avaliacao(Paciente paciente, Medico medico, int nota, String comentario) {
        this.paciente = paciente;
        this.medico = medico;
        this.nota = nota;
        this.comentario = comentario;
    }

    public int getNota() { return nota; }
    public String getComentario() { return comentario; }

    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
}
