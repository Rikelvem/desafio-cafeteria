package com.sistema.cafeteria.repository;

import com.sistema.cafeteria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository <Cliente, Long> {
}
