package com.api.Markteplace_de_livros.repository;

import com.api.Markteplace_de_livros.model.Autor;
import com.api.Markteplace_de_livros.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
    boolean existsByNome(String nome);
    boolean existsByNomeAndIdNot(String nome, Integer id);
    Optional<Autor> findByNome(String nome);
}