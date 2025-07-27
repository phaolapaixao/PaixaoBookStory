package com.api.Markteplace_de_livros.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "livros_vendedor")
public class VendedorLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_livros_vendedor;
    @Column( nullable = false)
    private String idioma;
    @Column( nullable = false)
    private String formato;
    @Column(precision = 10, scale = 2)
    private BigDecimal preco;
    @Column( nullable = false)
    private String condicao;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "vendedor_id",  nullable = false)
    private Vendedor vendedor;

    public Integer getId_livros_vendedor() {
        return id_livros_vendedor;
    }

    public void setId_livros_vendedor(Integer id_livros_vendedor) {
        this.id_livros_vendedor = id_livros_vendedor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

}
