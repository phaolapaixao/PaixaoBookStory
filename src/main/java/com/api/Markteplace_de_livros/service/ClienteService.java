package com.api.Markteplace_de_livros.service;

import com.api.Markteplace_de_livros.model.Cliente;
import com.api.Markteplace_de_livros.model.Endereco;
import com.api.Markteplace_de_livros.repository.ClienteRepository;
import com.api.Markteplace_de_livros.repository.EnderecoRepository;
import com.api.Markteplace_de_livros.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

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
        Optional<Cliente> existente = clienteRepository.findByEmail(cliente.getEmail());

        if (existente.isPresent() && !existente.get().getId().equals(cliente.getId())) {
            throw new RuntimeException("Email já está em uso");
        }

        return clienteRepository.save(cliente);
    }
    @Transactional
    public void delete(Integer id) {
        pedidoRepository.updateStatusPedidosPorCliente(id, "CANCELADO");
        pedidoRepository.removerClienteDosPedidos(id);
        clienteRepository.deleteById(id);
    }
    public boolean temPedidosPendentes(Integer clienteId) {
        return pedidoRepository.existsByClienteIdAndStatus(clienteId, "PENDENTE");
    }

    public Cliente salvarClienteComEndereco(Cliente cliente, Endereco endereco) {
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        cliente.setEndereco(enderecoSalvo);

        return clienteRepository.save(cliente);
    }

}
