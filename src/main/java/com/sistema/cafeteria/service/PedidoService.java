package com.sistema.cafeteria.service;

import com.sistema.cafeteria.model.ItemPedido;
import com.sistema.cafeteria.model.Pedido;
import com.sistema.cafeteria.model.Produto;
import com.sistema.cafeteria.repository.PedidoRepository;
import com.sistema.cafeteria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository; // Adiciona o repositório do Produto

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository; // Injeção de dependência do ProdutoRepository
    }

    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
    }

    public Pedido criar(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            item.setPedido(pedido);

            // Agora usando o ProdutoRepository para buscar o Produto pelo ID
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: ID " + item.getProduto().getId()));

            item.setProduto(produto); // agora tem preço!
        }

        double total = pedido.getItens().stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                .sum();

        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }

    public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
        Pedido pedido = buscarPorId(id);
        pedido.setItens(pedidoAtualizado.getItens());

        double total = pedido.getItens().stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                .sum();

        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }

    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }
}
