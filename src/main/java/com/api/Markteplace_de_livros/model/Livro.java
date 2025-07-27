package com.api.Markteplace_de_livros.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livro")
    private Integer id;

    @Column(nullable = false)
    private Year ano_publicacao;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String titulo;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String img;

    @ManyToOne
    @JoinColumn(name = "editora_id")
    private Editora editora;

    @ManyToMany
    @JoinTable(
            name = "categoria_livros",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();

    @OneToMany(mappedBy = "livro")
    private Set<VendedorLivro> vendedoresLivros = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Year getAno_publicacao() {
        return ano_publicacao;
    }

    public void setAno_publicacao(Year ano_publicacao) {
        this.ano_publicacao = ano_publicacao;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    public Set<VendedorLivro> getVendedoresLivros() {
        return vendedoresLivros;
    }

    public void setVendedoresLivros(Set<VendedorLivro> vendedoresLivros) {
        this.vendedoresLivros = vendedoresLivros;
    }
}
