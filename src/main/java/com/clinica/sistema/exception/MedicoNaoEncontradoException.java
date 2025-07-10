package com.clinica.sistema.exception;

public class MedicoNaoEncontradoException extends RuntimeException {
    public MedicoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}