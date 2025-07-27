package com.api.Markteplace_de_livros.service;

import com.api.Markteplace_de_livros.dto.RegistroDTO;
import com.api.Markteplace_de_livros.model.Vendedor;
import com.api.Markteplace_de_livros.repository.VendedorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final VendedorService vendedorService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(VendedorService vendedorService, PasswordEncoder passwordEncoder) {
        this.vendedorService = vendedorService;
        this.passwordEncoder = passwordEncoder;
    }

    public Vendedor autenticar(String email, String senhaDigitada) {

        Vendedor vendedor = vendedorService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail não encontrado"));

        boolean senhaCorreta = passwordEncoder.matches(senhaDigitada, vendedor.getSenha());

        if (!senhaCorreta) {
            throw new RuntimeException("Senha inválida");
        }

        return vendedor;
    }

    public void registerVendedor(RegistroDTO registroDTO) {
        if (!registroDTO.getSenha().equals(registroDTO.getConfirmarSenha())) {
            throw new RuntimeException("Senhas não coincidem");
        }

        Vendedor vendedor = new Vendedor();
        vendedor.setNome_fantasia(registroDTO.getNomeFantasia());
        vendedor.setEmail(registroDTO.getEmail());
        vendedor.setCnpj(registroDTO.getCnpj());
        vendedor.setTelefone(registroDTO.getTelefone());
        vendedor.setSenha(passwordEncoder.encode(registroDTO.getSenha()));

        vendedorService.salvarVendedor(vendedor);
    }
}