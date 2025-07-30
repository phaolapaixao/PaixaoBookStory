package com.api.Markteplace_de_livros.repository;

import com.api.Markteplace_de_livros.dto.LivroClienteDTO;
import com.api.Markteplace_de_livros.dto.LivroVendedorDTO;
import com.api.Markteplace_de_livros.model.Livro;
import com.api.Markteplace_de_livros.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
    @Query("SELECT vl.livro FROM VendedorLivro vl WHERE vl.vendedor = :vendedor")
    List<Livro> findByVendedor(@Param("vendedor") Vendedor vendedor);

    @Query("SELECT DISTINCT l FROM Livro l " +
            "LEFT JOIN l.autores a " +
            "LEFT JOIN l.categorias c " +
            "LEFT JOIN l.editora e " +
            "WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(a.nome) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(e.nome) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Livro> pesquisarLivros(@Param("termo") String termo);
    @Query("SELECT l FROM Livro l JOIN l.categorias c WHERE c.id = :categoriaId")
    List<Livro> findByCategoriaId(@Param("categoriaId") Integer categoriaId);
}


