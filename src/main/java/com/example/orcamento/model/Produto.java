package com.example.orcamento.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class Produto {

    @NotBlank(message = "Nome do produto e obrigatorio")
    private String nome;

    private String descricao;

    @NotNull(message = "Quantidade e obrigatoria")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    private Integer quantidade;

    @NotNull(message = "Valor unitario e obrigatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "Valor unitario deve ser maior que zero")
    private BigDecimal valorUnitario;

    public Produto() {
    }

    public Produto(String nome, String descricao, Integer quantidade, BigDecimal valorUnitario) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getSubtotal() {
        if (quantidade == null || valorUnitario == null) {
            return BigDecimal.ZERO;
        }
        return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
