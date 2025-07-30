package com.api.Markteplace_de_livros.repository;

import com.api.Markteplace_de_livros.model.VendasVendedorMes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VendasVendedorMesRepository extends JpaRepository<VendasVendedorMes, Integer> {
    List<VendasVendedorMes> findByVendedorId(Integer vendedorId);
}
