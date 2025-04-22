package com.sistema.cafeteria.service;

import com.sistema.cafeteria.dto.request.ProdutoRequestDTO;
import com.sistema.cafeteria.dto.response.ProdutoResponseDTO;
import com.sistema.cafeteria.model.Categoria;
import com.sistema.cafeteria.model.Produto;
import com.sistema.cafeteria.repository.CategoriaRepository;
import com.sistema.cafeteria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProdutoResponseDTO> listar() {
        return produtoRepository.findAll()
                .stream()
                .map(this::convertToProdutoResponseDTO)
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        return convertToProdutoResponseDTO(produto);
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + dto.getCategoriaId()));

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(categoria);
        produto.setDescricao(dto.getDescricao());

        Produto salvo = produtoRepository.save(produto);
        return convertToProdutoResponseDTO(salvo);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoAtualizado) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));

        Categoria categoria = categoriaRepository.findById(produtoAtualizado.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + produtoAtualizado.getCategoriaId()));

        produto.setNome(produtoAtualizado.getNome());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setCategoria(categoria);
        produto.setDescricao(produtoAtualizado.getDescricao());

        Produto atualizado = produtoRepository.save(produto);
        return convertToProdutoResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    private ProdutoResponseDTO convertToProdutoResponseDTO(Produto produto) {
        ProdutoResponseDTO response = new ProdutoResponseDTO();
        response.setId(produto.getId());
        response.setNome(produto.getNome());
        response.setPreco(produto.getPreco());
        response.setCategoriaNome(produto.getCategoria().getNome());
        return response;
    }
}
