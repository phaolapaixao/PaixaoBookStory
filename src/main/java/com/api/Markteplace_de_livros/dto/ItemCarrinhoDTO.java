package com.api.Markteplace_de_livros.dto;

import java.math.BigDecimal;

public class ItemCarrinhoDTO {
    private Integer livroId;
    private Integer quantidade;
    private BigDecimal preco;

    public Integer getLivroId() {
        return livroId;
    }

    public void setLivroId(Integer livroId) {
        this.livroId = livroId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
