package com.clinica.sitema.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "medico_table")
public class Medico extends Pessoa {

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    private String planoDeSaude;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes = new ArrayList<>();


    public Medico(String nome, String senha, Especialidade especialidade, String planoDeSaude) {
        super(nome, senha);
        this.especialidade = especialidade;
        this.planoDeSaude = planoDeSaude;
    }

    public Especialidade getEspecialidade() { return especialidade; }
    public String getPlanoDeSaude() { return planoDeSaude; }
    public List<Avaliacao> getAvaliacoes() { return avaliacoes; }

    public double getMediaEstrelas() {
        if (avaliacoes == null || avaliacoes.isEmpty()) return 0;
        return avaliacoes.stream().mapToInt(Avaliacao::getNota).average().orElse(0);
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
    public void setPlanoDeSaude(String planoDeSaude) {
        this.planoDeSaude = planoDeSaude;
    }
    public void addAvaliacao(Avaliacao avaliacao) {
        avaliacoes.add(avaliacao);
        avaliacao.setMedico(this);
    }

    public void removeAvaliacao(Avaliacao avaliacao) {
        avaliacoes.remove(avaliacao);
        avaliacao.setMedico(null);
    }


    // setters, equals/hashCode, etc...
}
