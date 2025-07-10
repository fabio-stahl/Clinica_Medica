package com.clinica.sistema.exception;

public class ConsultaDuplicadaException extends RuntimeException {
    public ConsultaDuplicadaException(String mensagem) {
        super(mensagem);
    }
}