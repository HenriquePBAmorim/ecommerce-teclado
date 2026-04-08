package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SwitchRequestDTO(
        @NotBlank(message = "O nome do switch é obrigatório") @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres") String nome,

        @NotBlank(message = "O fabricante do switch é obrigatório") @Size(min = 2, max = 50, message = "O fabricante deve ter entre 2 e 50 caracteres") String fabricante,

        @NotNull(message = "O tipo do switch é obrigatório") Long idTipoSwitch,

        @NotNull(message = "A força de atuação é obrigatória") @Min(value = 10, message = "A força de atuação deve ser maior que 10g") Double forcaAtuacao) {
}