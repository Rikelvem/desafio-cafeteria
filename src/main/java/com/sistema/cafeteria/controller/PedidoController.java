package com.sistema.cafeteria.controller;

import com.sistema.cafeteria.model.Pedido;
import com.sistema.cafeteria.service.PedidoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pedido> listar() {
        return service.listar();
    }

    @PostMapping
    public Pedido criar(Pedido pedido) {
        return service.criar(pedido);
    }

    @DeleteMapping("/{id}")
    public void deletar(Long id) {
        service.deletar(id);
    }
}
