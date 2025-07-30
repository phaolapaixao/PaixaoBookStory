package com.api.Markteplace_de_livros.controller;

import com.api.Markteplace_de_livros.model.VendasVendedorMes;
import com.api.Markteplace_de_livros.model.Vendedor;
import com.api.Markteplace_de_livros.service.VendasVendedorMesService;
import com.api.Markteplace_de_livros.service.VendedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/vendedores")
public class VendasVendedorMesController {

    private final VendasVendedorMesService service;
    private final VendedorService vendedorService;

    public VendasVendedorMesController(VendasVendedorMesService service, VendedorService vendedorService) {
        this.service = service;
        this.vendedorService = vendedorService;
    }

    // Retorna os relatórios em JSON (usado pelo modal via fetch)
    @GetMapping("/relatorio")
    @ResponseBody
    public List<VendasVendedorMes> listarRelatorios(Principal principal) {
        String email = principal.getName();
        Vendedor vendedorLogado = vendedorService.findByEmail(email);
        if (vendedorLogado == null) return List.of();
        return service.listarPorVendedor(vendedorLogado.getId());
    }
}
