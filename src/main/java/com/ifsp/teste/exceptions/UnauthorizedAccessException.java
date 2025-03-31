package com.ifsp.teste.exceptions;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {
        super("você precisa estar logado para acessar essa página!");
    }

    public UnauthorizedAccessException(String mensagem) {
        super(mensagem);
    }
}