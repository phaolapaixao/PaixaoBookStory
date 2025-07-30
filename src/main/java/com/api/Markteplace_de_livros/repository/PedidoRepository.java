package com.api.Markteplace_de_livros.repository;

import com.api.Markteplace_de_livros.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    boolean existsByClienteIdAndStatus(Integer clienteId, String status);

    @Modifying
    @Transactional
    @Query("UPDATE Pedido p SET p.status = :status WHERE p.cliente.id = :clienteId")
    void updateStatusPedidosPorCliente(@Param("clienteId") Integer clienteId, @Param("status") String status);

    @Modifying
    @Transactional
    @Query("UPDATE Pedido p SET p.cliente = NULL WHERE p.cliente.id = :clienteId")
    void removerClienteDosPedidos(@Param("clienteId") Integer clienteId);

    @Query(value = "CALL PedidosPendentes(:vendedorId)", nativeQuery = true)
    List<Object[]> listarPedidosPendentes(@Param("vendedorId") int vendedorId);

    @Modifying
    @Transactional
    @Query(value = "CALL AtualizarStatusPedido(:pedidoId, :status)", nativeQuery = true)
    void atualizarStatusPedido(@Param("pedidoId") int pedidoId, @Param("status") String status);

    @Query(value = "CALL ClientesQueMaisCompraram(:vendedorId)", nativeQuery = true)
    List<Object[]> clientesQueMaisCompraram(@Param("vendedorId") int vendedorId);
}
