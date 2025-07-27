package com.api.Markteplace_de_livros.security;

import com.api.Markteplace_de_livros.model.Vendedor;
import com.api.Markteplace_de_livros.repository.VendedorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomVendedorDetailsService implements UserDetailsService {

    private final VendedorRepository vendedorRepository;

    public CustomVendedorDetailsService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Vendedor vendedor = vendedorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Vendedor não encontrado com o email: " + email));

        return new VendedorDetailsImpl(vendedor);
    }
}