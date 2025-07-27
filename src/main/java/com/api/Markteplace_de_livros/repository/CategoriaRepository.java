package com.api.Markteplace_de_livros.repository;
import com.api.Markteplace_de_livros.model.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends
        JpaRepository<Categoria, Integer> {
// Métodos CRUD básicos já vêm do JpaRepository
}

