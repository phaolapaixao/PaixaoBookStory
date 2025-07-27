package com.api.Markteplace_de_livros.facade;


import com.api.Markteplace_de_livros.dto.LivroClienteDTO;
import com.api.Markteplace_de_livros.service.LivroService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroFacade {

    private final LivroService livroService;

    public LivroFacade(LivroService livroService) {
        this.livroService = livroService;
    }

    public List<LivroClienteDTO> buscarLivrosParaCliente() {
        return livroService.buscarTodosParaCliente();
    }

}
