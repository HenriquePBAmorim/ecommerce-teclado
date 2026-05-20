package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDTO(
        @NotNull(message = "O ID do teclado é obrigatório") Long idTeclado,

        @NotNull(message = "A quantidade é obrigatória") @Min(value = 1, message = "A quantidade mínima deve ser 1") Integer quantidade) {
}