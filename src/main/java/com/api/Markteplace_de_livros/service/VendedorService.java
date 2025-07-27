package com.api.Markteplace_de_livros.service;

import com.api.Markteplace_de_livros.model.Vendedor;
import com.api.Markteplace_de_livros.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class VendedorService {
    private final VendedorRepository vendedorRepository;

    public VendedorService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    public Optional<Vendedor> buscarPorEmail(String email) {
        return vendedorRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return vendedorRepository.existsByEmail(email);
    }

    public boolean existsByCnpj(String cnpj) {
        return vendedorRepository.existsByCnpj(cnpj);
    }
    public Vendedor findByEmail(String email) {
        return vendedorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado com o email: " + email));
    }


    public Vendedor salvarVendedor(Vendedor vendedor) {
        if (existsByEmail(vendedor.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }
        if (existsByCnpj(vendedor.getCnpj())) {
            throw new RuntimeException("CNPJ já cadastrado");
        }

        return vendedorRepository.save(vendedor);
    }
}