package com.clinica.sistema.exception;

public class PacienteNaoEncontradoException extends RuntimeException {
    public PacienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}