package com.clinica.sitema.model;

public class Avaliacao {
    private Paciente paciente;
    private Medico medico;
    private int nota; // 1 a 5
    private String comentario;

    public Avaliacao(Paciente paciente, Medico medico, int nota, String comentario) {
        this.paciente = paciente;
        this.medico = medico;
        this.nota = nota;
        this.comentario = comentario;
    }

    public int getNota() { return nota; }
    public String getComentario() { return comentario; }

    // outros getters
}
