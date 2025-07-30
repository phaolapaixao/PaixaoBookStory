package com.api.Markteplace_de_livros.controller;

import com.api.Markteplace_de_livros.dto.ClienteTopDTO;
import com.api.Markteplace_de_livros.dto.PedidoPendenteDTO;
import com.api.Markteplace_de_livros.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pendentes/{vendedorId}")
    public List<PedidoPendenteDTO> getPedidosPendentes(@PathVariable int vendedorId) {
        return pedidoService.listarPedidosPendentes(vendedorId);
    }

    @PutMapping("/{pedidoId}/status")
    public ResponseEntity<String> atualizarStatus(@PathVariable int pedidoId, @RequestParam String status) {
        pedidoService.atualizarStatusPedido(pedidoId, status);
        return ResponseEntity.ok("Status atualizado com sucesso!");
    }

    @GetMapping("/clientes-top/{vendedorId}")
    public List<ClienteTopDTO> getClientesTop(@PathVariable int vendedorId) {
        return pedidoService.clientesQueMaisCompraram(vendedorId);
    }
}


