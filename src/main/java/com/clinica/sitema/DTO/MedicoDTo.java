package com.clinica.sitema.DTO;

public class MedicoDTo {
    private String nome;
    private String especialidade;
    private String planoDeSaude;
    private String senha;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getPlanoDeSaude() { return planoDeSaude; }
    public void setPlanoDeSaude(String planoDeSaude) { this.planoDeSaude = planoDeSaude; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}