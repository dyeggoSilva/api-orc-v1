package com.example.orcamento.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Orcamento {

    private String id;

    @NotBlank(message = "Nome do cliente e obrigatorio")
    private String nomeCliente;

    @NotBlank(message = "CPF/CNPJ e obrigatorio")
    private String cpfCnpj;

    @NotNull(message = "Data de validade e obrigatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataValidade;

    @NotEmpty(message = "O orcamento deve ter ao menos um produto")
    @Valid
    private List<Produto> produtos;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCriacao = LocalDate.now();

    public Orcamento() {
    }

    public BigDecimal getValorTotal() {
        if (produtos == null) {
            return BigDecimal.ZERO;
        }
        return produtos.stream()
                .map(Produto::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
