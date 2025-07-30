package com.api.Markteplace_de_livros.security;

import com.api.Markteplace_de_livros.model.Cliente;
import com.api.Markteplace_de_livros.model.Vendedor;
import com.api.Markteplace_de_livros.repository.ClienteRepository;
import com.api.Markteplace_de_livros.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Vendedor vendedor = vendedorRepository.findByEmail(email).orElse(null);
        if (vendedor != null) {
            return new VendedorDetailsImpl(vendedor);
        }

        // Se não for vendedor, tenta cliente
        Cliente cliente = clienteRepository.findByEmail(email).orElse(null);
        if (cliente != null) {
            return User.builder()
                    .username(cliente.getEmail())
                    .password(cliente.getSenha())
                    .roles("CLIENTE")
                    .build();
        }

        // Nenhum usuário encontrado
        throw new UsernameNotFoundException("Usuário não encontrado: " + email);
    }
}
