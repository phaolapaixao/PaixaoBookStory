package com.api.Markteplace_de_livros.controller;

import com.api.Markteplace_de_livros.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendedor")
public class VendedorPageController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/dashboard/{vendedorId}")
    public String dashboard(@PathVariable int vendedorId, Model model) {
        model.addAttribute("vendedorId", vendedorId);
        model.addAttribute("pedidosPendentes", pedidoService.listarPedidosPendentes(vendedorId));
        model.addAttribute("clientesTop", pedidoService.clientesQueMaisCompraram(vendedorId));
        return "vendedor/index";
    }
}
