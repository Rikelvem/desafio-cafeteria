package com.sistema.cafeteria.service;

import com.sistema.cafeteria.dto.request.PedidoRequestDTO;
import com.sistema.cafeteria.dto.request.PedidoRequestDTO.ItemPedidoRequestDTO;
import com.sistema.cafeteria.dto.response.PedidoResponseDTO;
import com.sistema.cafeteria.model.Cliente;
import com.sistema.cafeteria.model.Pedido;
import com.sistema.cafeteria.model.Produto;
import com.sistema.cafeteria.repository.ClienteRepository;
import com.sistema.cafeteria.repository.PedidoRepository;
import com.sistema.cafeteria.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    void deveCriarPedidoComSucesso() {
        Cliente cliente = new Cliente(1L, "João da Silva", "joao@email.com");
        Produto produto1 = new Produto(1L, "Café Expresso", "Forte", 5.5, null);
        Produto produto2 = new Produto(2L, "Cappuccino", "Com leite", 6.0, null);

        PedidoRequestDTO requestDTO = new PedidoRequestDTO();
        requestDTO.setClienteId(1L);
        requestDTO.setItens(List.of(
                new ItemPedidoRequestDTO(1L, 2),
                new ItemPedidoRequestDTO(2L, 1)
        ));

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto1));
        when(produtoRepository.findById(2L)).thenReturn(Optional.of(produto2));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido pedido = invocation.getArgument(0);
            pedido.setId(1L);
            return pedido;
        });

        PedidoResponseDTO response = pedidoService.criar(requestDTO);

        assertEquals("João da Silva", response.getNomeCliente());
        assertEquals(17.0, response.getTotal()); // 5.5 * 2 + 6.0
        assertEquals(2, response.getItens().size());
        assertEquals("Café Expresso", response.getItens().get(0).getNomeProduto());
        assertEquals("Cappuccino", response.getItens().get(1).getNomeProduto());
    }
}
