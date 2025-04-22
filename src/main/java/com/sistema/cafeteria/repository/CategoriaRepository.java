package com.sistema.cafeteria.repository;

import com.sistema.cafeteria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
