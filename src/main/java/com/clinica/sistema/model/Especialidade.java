package com.clinica.sistema.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Especialidade {
    CARDIOLOGIA("Cardiologia"),
    DERMATOLOGIA("Dermatologia"),
    ENDOCRINOLOGIA("Endocrinologia"),
    GASTROENTEROLOGIA("Gastroenterologia"),
    GERIATRIA("Geriatria"),
    GINECOLOGIA("Ginecologia"),
    NEUROLOGIA("Neurologia"),
    OFTALMOLOGIA("Oftalmologia"),
    ORTOPEDIA("Ortopedia"),
    PEDIATRIA("Pediatria"),
    PSIQUIATRIA("Psiquiatria"),
    UROLOGIA("Urologia");

    private final String descricao;

    Especialidade(String descricao) {
        this.descricao = descricao;
    }

    // ----------------- usado no seu import CSV -----------------
    public static Especialidade valueOfDescricao(String campo) {
        for (Especialidade e : values()) {
            if (e.getDescricao().equalsIgnoreCase(campo.trim())) {
                return e;
            }
        }
        throw new IllegalArgumentException("Especialidade inválida: " + campo);
    }

    // ----------------- usado no seu controller de update -------------
    public boolean equalsIgnoreCase(String especialidade) {
        if (especialidade == null || descricao == null) return false;
        return descricao.equalsIgnoreCase(especialidade);
    }

    // mantém aquele fromDescricao original (para seu PUT/update)
    public static Especialidade fromDescricao(String descricao) {
        for (Especialidade e : values()) {
            if (e.getDescricao().equalsIgnoreCase(descricao.trim())) {
                return e;
            }
        }
        throw new IllegalArgumentException("Especialidade inválida: " + descricao);
    }

    // ----------------- JSON → e ← Converter para eliminar 415 -----------------
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Especialidade fromJson(String valor) {
        for (Especialidade e : values()) {
            if (e.name().equalsIgnoreCase(valor)
             || e.descricao.equalsIgnoreCase(valor.trim())) {
                return e;
            }
        }
        throw new IllegalArgumentException("Especialidade inválida: " + valor);
    }
}
