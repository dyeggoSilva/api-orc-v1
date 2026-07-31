package com.example.orcamento.exception;

public class OrcamentoNaoEncontradoException extends RuntimeException {

    public OrcamentoNaoEncontradoException(String id) {
        super("Orcamento nao encontrado com id: " + id);
    }
}
