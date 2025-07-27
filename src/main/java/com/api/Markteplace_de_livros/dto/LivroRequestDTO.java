package com.api.Markteplace_de_livros.dto;

import com.api.Markteplace_de_livros.model.Livro;
import com.api.Markteplace_de_livros.model.VendedorLivro;

import java.util.List;

public class LivroRequestDTO {
    private Livro livro;
    private VendedorLivro vendedorLivro;
    private List<Integer> categorias;
    private List<Integer> autores;
    private String imagemBase64;
    private String contentType;

    // Getters e Setters
    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public VendedorLivro getVendedorLivro() {
        return vendedorLivro;
    }

    public void setVendedorLivro(VendedorLivro vendedorLivro) {
        this.vendedorLivro = vendedorLivro;
    }

    public List<Integer> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Integer> categorias) {
        this.categorias = categorias;
    }

    public List<Integer> getAutores() {
        return autores;
    }

    public void setAutores(List<Integer> autores) {
        this.autores = autores;
    }

    public String getImagemBase64() {
        return imagemBase64;
    }

    public void setImagemBase64(String imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}