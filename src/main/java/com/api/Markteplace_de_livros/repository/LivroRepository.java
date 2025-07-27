package com.api.Markteplace_de_livros.repository;

import com.api.Markteplace_de_livros.model.Livro;
import com.api.Markteplace_de_livros.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
    @Query("SELECT vl.livro FROM VendedorLivro vl WHERE vl.vendedor = :vendedor")
    List<Livro> findByVendedor(@Param("vendedor") Vendedor vendedor);
}


