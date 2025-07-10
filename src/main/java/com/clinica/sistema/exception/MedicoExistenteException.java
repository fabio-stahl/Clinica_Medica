package com.clinica.sistema.exception;

public class MedicoExistenteException extends RuntimeException {
    public MedicoExistenteException(String mensagem) {
        super(mensagem);
    }
}