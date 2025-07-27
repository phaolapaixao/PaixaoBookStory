package com.api.Markteplace_de_livros.service;

import com.api.Markteplace_de_livros.model.Cliente;
import com.api.Markteplace_de_livros.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }

    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o email: " + email));
    }

    public Cliente salvarCliente(Cliente cliente) {
        if (existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }
        return clienteRepository.save(cliente);
    }
}
