package com.api.Markteplace_de_livros.config;

import com.api.Markteplace_de_livros.security.CustomClienteDetailsService;
import com.api.Markteplace_de_livros.security.CustomVendedorDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomVendedorDetailsService vendedorDetailsService;
    private final CustomClienteDetailsService clienteDetailsService;

    public SecurityConfig(CustomVendedorDetailsService vendedorDetailsService,
                          CustomClienteDetailsService clienteDetailsService) {
        this.vendedorDetailsService = vendedorDetailsService;
        this.clienteDetailsService = clienteDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(vendedorDetailsService).passwordEncoder(passwordEncoder());
        auth.userDetailsService(clienteDetailsService).passwordEncoder(passwordEncoder());
        return auth.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain vendedorSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/vendedores/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/vendedores/login",
                                "/vendedores/registro",
                                "/vendedores/cadastrar",
                                "/vendedores/index",
                                "/vendedor/**"  // Permite acesso aos repositórios
                        ).permitAll()
                        .anyRequest().hasRole("VENDEDOR")
                )
                .formLogin(form -> form
                        .loginPage("/vendedores/login")
                        .loginProcessingUrl("/vendedores/login")
                        .defaultSuccessUrl("/vendedores/index", true)
                        .failureUrl("/vendedores/login?error=true")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/vendedores/logout")
                        .logoutSuccessUrl("/vendedores/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain clienteSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/clientes/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/clientes/login", "/clientes/registro", "/clientes/index").permitAll()
                        .anyRequest().hasRole("CLIENTE")
                )
                .formLogin(form -> form
                        .loginPage("/clientes/login")
                        .loginProcessingUrl("/clientes/login")
                        .defaultSuccessUrl("/clientes/index", true)
                        .failureUrl("/clientes/login?error=true")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/clientes/logout")
                        .logoutSuccessUrl("/clientes/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/static/**",
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/webjars/**",
                                "/errorr",
                                "/vendedor/**"  // Permite acesso aos repositórios
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }
}