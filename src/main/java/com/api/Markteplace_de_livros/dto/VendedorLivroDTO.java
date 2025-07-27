package com.api.Markteplace_de_livros.dto;

import com.api.Markteplace_de_livros.model.Livro;
import com.api.Markteplace_de_livros.model.Vendedor;

import java.math.BigDecimal;

public class VendedorLivroDTO {

    private Integer id_livros_vendedor;
    private String idioma;
    private String formato;
    private BigDecimal preco;
    private String condicao;
    private Livro livro;
    private Vendedor vendedor;

    public Integer getId_livros_vendedor() {
        return id_livros_vendedor;
    }

    public void setId(Integer id_livros_vendedor) {
        this.id_livros_vendedor = id_livros_vendedor;
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

