package com.api.Markteplace_de_livros.service;

import com.api.Markteplace_de_livros.dto.RegistroClienteDTO;
import com.api.Markteplace_de_livros.model.Cliente;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteAuthService {

    private final ClienteService clienteService;
    private final PasswordEncoder passwordEncoder;

    public ClienteAuthService(ClienteService clienteService, PasswordEncoder passwordEncoder) {
        this.clienteService = clienteService;
        this.passwordEncoder = passwordEncoder;
    }

    public Cliente autenticar(String email, String senhaDigitada) {
        System.out.println("Tentando autenticar cliente: " + email);

        Cliente cliente = clienteService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail não encontrado"));

        boolean senhaCorreta = passwordEncoder.matches(senhaDigitada, cliente.getSenha());
        System.out.println("Senha correta? " + senhaCorreta);

        if (!senhaCorreta) {
            throw new RuntimeException("Senha inválida");
        }

        return cliente;
    }

    public void registerCliente(RegistroClienteDTO registroDTO) {
        if (!registroDTO.getSenha().equals(registroDTO.getConfirmarSenha())) {
            throw new RuntimeException("Senhas não coincidem");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(registroDTO.getNome());
        cliente.setEmail(registroDTO.getEmail());
        cliente.setCpf(registroDTO.getCpf());
        cliente.setTelefone(registroDTO.getTelefone());
        cliente.setDataNascimento(registroDTO.getDataNascimento());
        cliente.setDataCadastro(registroDTO.getDataCadastro());
        cliente.setSenha(passwordEncoder.encode(registroDTO.getSenha()));

        clienteService.salvarCliente(cliente);
    }
}
