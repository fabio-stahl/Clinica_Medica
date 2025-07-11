package com.clinica.sistema.model;
import com.fasterxml.jackson.annotation.JsonCreator;

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

    public String getDescricao() {
        return descricao;
    }

    public boolean equalsIgnoreCase(String especialidade) {
        if (especialidade == null || descricao == null) return false;
        return descricao.equalsIgnoreCase(especialidade);
    }

    @JsonCreator
    public static Especialidade fromDescricao(String descricao) {
        for (Especialidade e : Especialidade.values()) {
            if (e.getDescricao().equalsIgnoreCase(descricao)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Especialidade inválida: " + descricao);
    }
}