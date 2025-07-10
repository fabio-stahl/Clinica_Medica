package com.clinica.sistema.exception;

public class PacienteExistenteException extends RuntimeException {
    public PacienteExistenteException(String mensagem) {
        super(mensagem);
    }
}