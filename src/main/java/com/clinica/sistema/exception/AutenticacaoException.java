package com.clinica.sistema.exception;

public class AutenticacaoException extends RuntimeException {
    public AutenticacaoException(String mensagem){
        super(mensagem);
    }
}
