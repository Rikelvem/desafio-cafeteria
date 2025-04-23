package com.sistema.cafeteria.service;

import com.sistema.cafeteria.dto.request.PedidoRequestDTO;
import com.sistema.cafeteria.dto.response.PedidoResponseDTO;
import com.sistema.cafeteria.exception.ResourceNotFoundException;
import com.sistema.cafeteria.model.Cliente;
import com.sistema.cafeteria.model.ItemPedido;
import com.sistema.cafeteria.model.Pedido;
import com.sistema.cafeteria.model.Produto;
import com.sistema.cafeteria.repository.ClienteRepository;
import com.sistema.cafeteria.repository.PedidoRepository;
import com.sistema.cafeteria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<PedidoResponseDTO> listar(){
        return pedidoRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));
    }

    public PedidoResponseDTO criar(PedidoRequestDTO pedidoRequestDTO) {
        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + pedidoRequestDTO.getClienteId()));

        Pedido pedido = new Pedido();
        pedido.setNomeCliente(cliente.getNome());

        List<ItemPedido> itens = pedidoRequestDTO.getItens().stream().map(itemDTO -> {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + itemDTO.getProdutoId()));
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setPedido(pedido);
            return itemPedido;
        }).collect(Collectors.toList());

        pedido.setItens(itens);

        double total = itens.stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                .sum();
        pedido.setTotal(total);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

       return mapToResponseDTO(pedidoSalvo);
    }

    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedidoExistente = buscarPorId(id);

        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + pedidoRequestDTO.getClienteId()));

        pedidoExistente.setNomeCliente(cliente.getNome());

        List<ItemPedido> itens = pedidoRequestDTO.getItens().stream().map(itemDTO -> {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + itemDTO.getProdutoId()));
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setPedido(pedidoExistente);
            return itemPedido;
        }).collect(Collectors.toList());

        pedidoExistente.setItens(itens);

        double total = itens.stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                .sum();
        pedidoExistente.setTotal(total);

        Pedido pedidoAtualizado = pedidoRepository.save(pedidoExistente);
        return mapToResponseDTO(pedidoAtualizado);
    }

    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }

    private PedidoResponseDTO mapToResponseDTO(Pedido pedido) {
        List<PedidoResponseDTO.ItemPedidoDTO> itensDTO = pedido.getItens().stream()
                .map(item -> new PedidoResponseDTO.ItemPedidoDTO(
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getProduto().getPreco(),
                        item.getQuantidade()
                )).collect(Collectors.toList());

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getNomeCliente(),
                pedido.getTotal(),
                itensDTO
        );
    }
}
