package com.api.Markteplace_de_livros.controller;

import com.api.Markteplace_de_livros.dto.LivroClienteDTO;
import com.api.Markteplace_de_livros.model.Categoria;
import com.api.Markteplace_de_livros.model.Cliente;
import com.api.Markteplace_de_livros.facade.LivroFacade;
import com.api.Markteplace_de_livros.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    private final LivroFacade livroFacade;
    private final ClienteService clienteService;

    public CategoriaController(LivroFacade livroFacade, ClienteService clienteService) {
        this.livroFacade = livroFacade;
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public String listarLivrosPorCategoria(@PathVariable Integer id, Model model, Principal principal) {
        List<LivroClienteDTO> livros = livroFacade.buscarPorCategoria(id);
        model.addAttribute("livros", livros);

        Categoria categoria = livroFacade.buscarCategoriaPorId(id);
        model.addAttribute("categoria", categoria);

        List<Categoria> categorias = livroFacade.buscarCategorias();
        model.addAttribute("categorias", categorias);

        if (principal != null) {
            Cliente cliente = clienteService.findByEmail(principal.getName());
            model.addAttribute("cliente", cliente);
        }

        return "clientes/index";
    }
}

