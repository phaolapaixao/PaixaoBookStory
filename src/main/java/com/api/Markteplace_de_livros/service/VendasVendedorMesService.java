package com.api.Markteplace_de_livros.service;

import com.api.Markteplace_de_livros.model.VendasVendedorMes;
import com.api.Markteplace_de_livros.repository.VendasVendedorMesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendasVendedorMesService {

    private final VendasVendedorMesRepository repository;

    public VendasVendedorMesService(VendasVendedorMesRepository repository) {
        this.repository = repository;
    }

    public List<VendasVendedorMes> listarPorVendedor(Integer vendedorId) {
        return repository.findByVendedorId(vendedorId);
    }
}
