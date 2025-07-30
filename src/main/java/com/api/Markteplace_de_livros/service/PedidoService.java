package com.api.Markteplace_de_livros.service;

import com.api.Markteplace_de_livros.dto.ClienteTopDTO;
import com.api.Markteplace_de_livros.dto.PedidoPendenteDTO;
import com.api.Markteplace_de_livros.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<PedidoPendenteDTO> listarPedidosPendentes(int vendedorId) {
        List<Object[]> resultados = pedidoRepository.listarPedidosPendentes(vendedorId);
        return resultados.stream()
                .map(obj -> new PedidoPendenteDTO(
                        (Integer) obj[0],
                        (String) obj[1],
                        obj[2].toString(),
                        (String) obj[3]))
                .toList();
    }

    public void atualizarStatusPedido(int pedidoId, String status) {
        pedidoRepository.atualizarStatusPedido(pedidoId, status);
    }

    public List<ClienteTopDTO> clientesQueMaisCompraram(int vendedorId) {
        List<Object[]> resultados = pedidoRepository.clientesQueMaisCompraram(vendedorId);
        return resultados.stream()
                .map(obj -> new ClienteTopDTO(
                        (Integer) obj[0],
                        (String) obj[1],
                        ((Number) obj[2]).longValue(),
                        ((Number) obj[3]).longValue()))
                .toList();
    }
}

