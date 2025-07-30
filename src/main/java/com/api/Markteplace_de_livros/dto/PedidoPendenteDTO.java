package com.api.Markteplace_de_livros.dto;

public class PedidoPendenteDTO {
    private Integer idPedido;
    private String nomeCliente;
    private String dataPedido;
    private String status;

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PedidoPendenteDTO(Integer idPedido, String nomeCliente, String dataPedido, String status) {
        this.idPedido = idPedido;
        this.nomeCliente = nomeCliente;
        this.dataPedido = dataPedido;
        this.status = status;



    }
}