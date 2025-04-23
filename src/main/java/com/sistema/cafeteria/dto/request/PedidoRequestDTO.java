package com.sistema.cafeteria.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoRequestDTO {

    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "A lista de itens é obrigatória")
    private List<ItemPedidoDTO> itens;

    @Getter
    @Setter
    public static class ItemPedidoDTO {
        @NotNull(message = "ID do produto é obrigatório")
        private Long produtoId;

        @NotNull(message = "Quantidade é obrigatória")
        private Integer quantidade;
    }
}
