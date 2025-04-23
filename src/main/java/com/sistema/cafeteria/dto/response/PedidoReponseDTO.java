package com.sistema.cafeteria.dto.response;

import com.sistema.cafeteria.dto.request.PedidoRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoReponseDTO {

    private Long id;
    private String nomeCliente;
    private Double total;
    private List<ItemPedidoDTO> itens;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemPedidoDTO {
        private Long produtoId;
        private String nomeProduto;
        private Integer quantidade;
        private Double precoUnitario;
    }

}
