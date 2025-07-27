package com.api.Markteplace_de_livros.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "vendedores")
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vendedor")
    private Integer id;
    @Column( nullable = false)
    private String nome_fantasia;
    @Column(unique = true)
    private String email;
    @Column( nullable = false)
    private String cnpj;
    @Column( nullable = false)
    private String telefone;
    @Column( nullable = false)
    private String senha;
    @OneToMany(mappedBy = "vendedor")
    private Set<VendedorLivro> livrosVendidos = new HashSet<>();
    public String getPerfil() {
        return "VENDEDOR";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<VendedorLivro> getLivrosVendidos() {
        return livrosVendidos;
    }

    public void setLivrosVendidos(Set<VendedorLivro> livrosVendidos) {
        this.livrosVendidos = livrosVendidos;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}