package com.sistema.cafeteria.service;

import com.sistema.cafeteria.model.Pedido;
import com.sistema.cafeteria.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public List<Pedido> listar(){
        return repository.findAll();
    }

    public Pedido criar(Pedido pedido) {
        double total = pedido.getItens().stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                .sum();
        pedido.setTotal(total);

        pedido.getItens().forEach(itemPedido -> itemPedido.setPedido(pedido));

        return repository.save(pedido);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

}
