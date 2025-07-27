package com.api.Markteplace_de_livros.security;

import com.api.Markteplace_de_livros.model.Vendedor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class VendedorDetailsImpl implements UserDetails {

    private final Vendedor vendedor;

    public VendedorDetailsImpl(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
    }

    @Override
    public String getPassword() {
        return vendedor.getSenha();
    }

    @Override
    public String getUsername() {
        return vendedor.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }
}