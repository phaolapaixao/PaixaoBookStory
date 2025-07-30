package com.api.Markteplace_de_livros.repository;

import com.api.Markteplace_de_livros.model.Carrinho;
import com.api.Markteplace_de_livros.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Integer> {
    Optional<Carrinho> findByClienteAndStatus(Cliente cliente, String status);
}
