package com.sistema.cafeteria.repository;


import com.sistema.cafeteria.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository <Funcionario, Long> {
}
