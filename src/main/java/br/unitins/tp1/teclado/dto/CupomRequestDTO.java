package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CupomRequestDTO(
    @NotBlank(message = "O código do cupom é obrigatório") String codigo,
    @NotNull(message = "O percentual de desconto é obrigatório") @Positive(message = "O percentual deve ser positivo") Double percentualDesconto
) {
}
