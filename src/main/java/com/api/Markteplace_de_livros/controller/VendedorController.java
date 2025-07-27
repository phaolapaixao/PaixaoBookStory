package com.api.Markteplace_de_livros.controller;

import com.api.Markteplace_de_livros.model.Vendedor;
import com.api.Markteplace_de_livros.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/vendedores")
public class VendedorController {

    private final VendedorService vendedoresService;

    @Autowired
    public VendedorController(VendedorService vendedoresService) {
        this.vendedoresService = vendedoresService;
    }

    @GetMapping("/cadastrar")
    public String novocadastro(Model model) {
        model.addAttribute("vendedores", new Vendedor());
        return "vendedores/index";
    }

    @GetMapping("/index")
    public String paginaInicialVendedor(Model model, Principal principal) {
        String email = principal.getName();

        Vendedor vendedor = vendedoresService.findByEmail(email);
        model.addAttribute("vendedor", vendedor);

        return "vendedores/index";
    }

    @PostMapping("/cadastrar")
    @ResponseBody
    public ResponseEntity<?> cadastrarVendedor(@RequestBody Vendedor vendedor) {
        try {
            Map<String, String> errors = new HashMap<>();

            if (vendedor.getNome_fantasia() == null || vendedor.getNome_fantasia().isEmpty()) {
                errors.put("nomeError", "Nome fantasia é obrigatório");
            }

            if (vendedor.getEmail() == null || vendedor.getEmail().isEmpty()) {
                errors.put("emailError", "Email é obrigatório");
            }

            if (vendedor.getCnpj() != null) {
                String cnpjLimpo = vendedor.getCnpj().replaceAll("[^0-9]", "");
                vendedor.setCnpj(cnpjLimpo);
            }

            if (vendedor.getSenha() == null || vendedor.getSenha().isEmpty()) {
                errors.put("senhaError", "Senha é obrigatória");
            }

            if (!errors.isEmpty()) {
                return ResponseEntity.badRequest().body(errors);
            }

            if (vendedoresService.existsByEmail(vendedor.getEmail())) {
                errors.put("emailError", "Email já cadastrado");
            }

            if (vendedoresService.existsByCnpj(vendedor.getCnpj())) {
                errors.put("cnpjError", "CNPJ já cadastrado");
            }

            if (!errors.isEmpty()) {
                return ResponseEntity.badRequest().body(errors);
            }

            Vendedor novoVendedor = vendedoresService.salvarVendedor(vendedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoVendedor);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao cadastrar vendedor: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}