package com.clinica.sitema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "paciente")
public class Paciente extends Pessoa {
    private int idade;
    private String planoDeSaude;

    public Paciente() {
        super();
    }

    public Paciente(String nome, String senha, int idade, String planoDeSaude) {
        super(nome, senha);
        this.idade = idade;
        this.planoDeSaude = planoDeSaude;
    }

    public int getIdade() { return idade; }
    public String getPlanoDeSaude() { return planoDeSaude; }

    public boolean temPlano() {
        return planoDeSaude != null && !planoDeSaude.equalsIgnoreCase("não tenho");
    }
}
