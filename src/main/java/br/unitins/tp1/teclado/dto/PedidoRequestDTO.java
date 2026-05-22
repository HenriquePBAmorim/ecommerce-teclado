package br.unitins.tp1.teclado.dto;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PedidoRequestDTO(
        @NotNull(message = "O endereço de entrega é obrigatório") Long idEnderecoEntrega,

        @NotNull(message = "A forma de pagamento é obrigatória") Integer idFormaPagamento,

        Long idCartao,

        @NotEmpty(message = "O pedido deve conter pelo menos um item") List<ItemPedidoRequestDTO> itens) {
}