package com.api.Markteplace_de_livros.controller;

import com.api.Markteplace_de_livros.dto.LivroVendedorDTO;
import com.api.Markteplace_de_livros.model.VendedorLivro;
import com.api.Markteplace_de_livros.service.VendedorLivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livros_vendedor")
@CrossOrigin(origins = "*")
public class VendedorLivroController {

    @Autowired
    private VendedorLivroService vendedorLivroService;

    @PostMapping
    public ResponseEntity<?> criarVinculo(@RequestBody LivroVendedorDTO dto) {
        try {
            VendedorLivro novoVinculo = vendedorLivroService.salvarVendedorLivro(
                    dto.getLivro().getId(),
                    dto.getVendedor().getId(),
                    dto.getCondicao(),
                    dto.getFormato(),
                    dto.getPreco(),
                    dto.getIdioma()
            );
            return ResponseEntity.ok(novoVinculo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    java.util.Map.of("erro", e.getMessage())
            );
        }
    }

    public VendedorLivroService getVendedorLivroService() {
        return vendedorLivroService;
    }

    public void setVendedorLivroService(VendedorLivroService vendedorLivroService) {
        this.vendedorLivroService = vendedorLivroService;
    }
}