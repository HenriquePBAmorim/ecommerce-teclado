package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EstadoRequestDTO(
        @Size(max = 2, min = 2, message = "A sigla do estado deve conter exatamente 2 caracteres") @NotBlank(message = "A sigla do estado é obrigatória") String sigla,

        @NotBlank(message = "O nome do estado é obrigatório") @Size(min = 3, message = "O nome do estado deve conter no mínimo 3 caracteres") @Size(max = 60, message = "O nome do estado deve conter no máximo 60 caracteres") String nome,

        @NotNull(message = "A região do estado é obrigatória") Long idRegiao) {
}