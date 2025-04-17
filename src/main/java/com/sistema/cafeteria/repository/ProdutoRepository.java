package com.sistema.cafeteria.repository;

import com.sistema.cafeteria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository <Produto, Long>{
}
