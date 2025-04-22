package com.sistema.cafeteria.service;

import com.sistema.cafeteria.model.Funcionario;
import com.sistema.cafeteria.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public List<Funcionario> listar() {
        return repository.findAll();
    }

    public Funcionario criar(Funcionario funcionario) {
        return repository.save(funcionario);
    }

    public Funcionario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
    }

    public Funcionario atualizar(Long id, Funcionario funcionarioAtualizado) {
        Funcionario funcionario = buscarPorId(id);
        funcionario.setTelefone(funcionarioAtualizado.getTelefone());
        funcionario.setEmail(funcionarioAtualizado.getEmail());
        funcionario.setCargo(funcionarioAtualizado.getCargo());
        return repository.save(funcionario);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
