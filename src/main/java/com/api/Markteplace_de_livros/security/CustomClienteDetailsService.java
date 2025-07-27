package com.api.Markteplace_de_livros.security;

import com.api.Markteplace_de_livros.model.Cliente;
import com.api.Markteplace_de_livros.repository.ClienteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomClienteDetailsService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    public CustomClienteDetailsService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado com o email: " + email));

        return new ClienteDetailsImpl(cliente);
    }
}
