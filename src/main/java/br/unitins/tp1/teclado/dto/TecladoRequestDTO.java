package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TecladoRequestDTO(
        @NotBlank(message = "O nome do teclado é obrigatório") @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres") String nome,

        @NotNull(message = "O tipo do switch é obrigatório") Long idTipoSwitch,

        @NotNull(message = "O formato é obrigatório") Long idFormato,

        @NotNull(message = "A marca é obrigatória") Long idMarca,

        @NotNull(message = "O preço é obrigatório") @Min(value = 1, message = "O preço deve ser maior que zero") Double preco) {
}