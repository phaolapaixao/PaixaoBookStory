package com.api.Markteplace_de_livros.repository;

import com.api.Markteplace_de_livros.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditoraRepository extends
        JpaRepository<Editora, Integer> {
// Métodos CRUD básicos já vêm do JpaRepository
}
