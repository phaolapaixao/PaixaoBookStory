package com.api.Markteplace_de_livros.repository;

import com.api.Markteplace_de_livros.model.Livro;
import com.api.Markteplace_de_livros.model.Vendedor;
import com.api.Markteplace_de_livros.model.VendedorLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendedorLivroRepository extends JpaRepository<VendedorLivro, Integer> {

    boolean existsByLivroAndVendedor(Livro livro, Vendedor vendedor);
    void deleteByLivro(Livro livro);
    Optional<VendedorLivro> findByLivroAndVendedor(Livro livro, Vendedor vendedor);
    Optional<VendedorLivro> findByLivro(Livro livro);
    Optional<VendedorLivro> findByLivroId(Integer livroId);
    List<VendedorLivro> findAllByLivro(Livro livro);
}