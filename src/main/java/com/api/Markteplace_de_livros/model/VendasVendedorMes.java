package com.api.Markteplace_de_livros.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@IdClass(VendasVendedorMesId.class)
@Table(name = "vendas_vendedor_mes")
public class VendasVendedorMes{

    @Id
    @Column(name = "vendedor_id")
    private Integer vendedorId;

    @Id
    private Integer ano;

    @Id
    private Integer mes;

    @Column(name = "total_pedidos")
    private Long totalPedidos;

    @Column(name = "total_livros_vendidos")
    private Long totalLivrosVendidos;

    @Column(name = "total_vendas")
    private BigDecimal totalVendas;

    public Integer getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Integer vendedorId) {
        this.vendedorId = vendedorId;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Long getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(Long totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public Long getTotalLivrosVendidos() {
        return totalLivrosVendidos;
    }

    public void setTotalLivrosVendidos(Long totalLivrosVendidos) {
        this.totalLivrosVendidos = totalLivrosVendidos;
    }

    public BigDecimal getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(BigDecimal totalVendas) {
        this.totalVendas = totalVendas;
    }
}