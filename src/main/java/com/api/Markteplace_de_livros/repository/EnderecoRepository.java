package com.api.Markteplace_de_livros.repository;


import com.api.Markteplace_de_livros.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends
        JpaRepository<Endereco, Integer> {
}
