package com.clinica.sistema.model;

public class MedicoDTO {
    private String nome;
    private String especialidade;
    private String planoDeSaude;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getPlanoDeSaude() { return planoDeSaude; }
    public void setPlanoDeSaude(String planoDeSaude) { this.planoDeSaude = planoDeSaude; }
}
