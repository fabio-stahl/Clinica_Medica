package com.clinica.sitema.model;

import java.util.List;

public class Medico extends Pessoa {
    private final String especialidade;
    private final String planoDeSaude;
    private List<Avaliacao> avaliacoes;

    public Medico(String nome, String senha, String especialidade, String planoDeSaude) {
        super(nome, senha);
        this.especialidade = especialidade;
        this.planoDeSaude = planoDeSaude;
    }

    public String getEspecialidade() { return especialidade; }
    public String getPlanoDeSaude() { return planoDeSaude; }
    public List<Avaliacao> getAvaliacoes() { return avaliacoes; }

    public double getMediaEstrelas() {
        if (avaliacoes == null || avaliacoes.isEmpty()) return 0;
        return avaliacoes.stream().mapToInt(Avaliacao::getNota).average().orElse(0);
    }

    // setters, equals/hashCode, etc...
}
