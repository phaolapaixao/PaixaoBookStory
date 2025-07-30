package com.api.Markteplace_de_livros.dto;

import java.util.List;

public class PedidoRequestDTO {
    private String formaPagamento;
    private List<ItemCarrinhoDTO> itens;

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItemCarrinhoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinhoDTO> itens) {
        this.itens = itens;
    }
}
