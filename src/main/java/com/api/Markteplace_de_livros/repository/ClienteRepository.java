package com.api.Markteplace_de_livros.repository;

import com.api.Markteplace_de_livros.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByEmail(String email);
    boolean existsByEmail(String email);
}
