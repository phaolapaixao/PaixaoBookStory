package com.api.Markteplace_de_livros.dto;

public class ClienteTopDTO {
    private Integer idCliente;
    private String nomeCliente;
    private Long totalPedidos;
    private Long totalLivros;

    public ClienteTopDTO(Integer idCliente, String nomeCliente, Long totalPedidos, Long totalLivros) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.totalPedidos = totalPedidos;
        this.totalLivros = totalLivros;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Long getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(Long totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public Long getTotalLivros() {
        return totalLivros;
    }

    public void setTotalLivros(Long totalLivros) {
        this.totalLivros = totalLivros;
    }
}

