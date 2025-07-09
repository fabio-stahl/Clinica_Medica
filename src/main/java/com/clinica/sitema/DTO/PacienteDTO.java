package com.clinica.sitema.DTO;

public class PacienteDTO {
    private String nome;
    private int idade;
    private String planoDeSaude;
    private String senha;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getPlanoDeSaude() { return planoDeSaude; }
    public void setPlanoDeSaude(String planoDeSaude) { this.planoDeSaude = planoDeSaude; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}