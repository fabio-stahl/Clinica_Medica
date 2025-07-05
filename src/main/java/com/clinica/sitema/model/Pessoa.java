package com.clinica.sitema.model;

public abstract class Pessoa {
    protected String nome;
    protected String senha;

    public Pessoa(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome() { return nome; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}

