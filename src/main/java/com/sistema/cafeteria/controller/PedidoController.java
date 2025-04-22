package com.sistema.cafeteria.controller;

import com.sistema.cafeteria.model.Pedido;
import com.sistema.cafeteria.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/{id}")
    public Pedido buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Pedido criar(@RequestBody @Valid Pedido pedido) {
        return service.criar(pedido);
    }

    @PutMapping("/{id}")
    public Pedido atualizar(@PathVariable Long id, @RequestBody @Valid Pedido pedidoAtualizado) {
        return service.atualizar(id, pedidoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(Long id) {
        service.deletar(id);
    }
}
