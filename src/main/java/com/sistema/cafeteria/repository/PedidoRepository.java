package com.sistema.cafeteria.repository;

import com.sistema.cafeteria.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
