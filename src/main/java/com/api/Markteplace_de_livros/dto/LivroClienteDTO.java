package com.api.Markteplace_de_livros.dto;

import java.util.List;

public class LivroClienteDTO {

    private Integer id;
    private String titulo;
    private String isbn;
    private Integer anoPublicacao;
    private String img;
    private String nomeEditora;
    private List<String> categorias;
    private List<String> autores;
    private List<VendedorLivroDTO> vendedores;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNomeEditora() {
        return nomeEditora;
    }

    public void setNomeEditora(String nomeEditora) {
        this.nomeEditora = nomeEditora;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public List<String> getAutores() {
        return autores;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }

    public List<VendedorLivroDTO> getVendedores() {
        return vendedores;
    }

    public void setVendedores(List<VendedorLivroDTO> vendedores) {
        this.vendedores = vendedores;
    }

    public static class VendedorLivroDTO {
        private String nomeFantasia;
        private Double preco;
        private String idioma;
        private String formato;
        private String condicao;


        public Double getPreco() {
            return preco;
        }

        public void setPreco(Double preco) {
            this.preco = preco;
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

        public String getCondicao() {
            return condicao;
        }

        public void setCondicao(String condicao) {
            this.condicao = condicao;
        }

        public String getNomeFantasia() {
            return nomeFantasia;
        }

        public void setNomeFantasia(String nomeFantasia) {
            this.nomeFantasia = nomeFantasia;
        }
    }
}
