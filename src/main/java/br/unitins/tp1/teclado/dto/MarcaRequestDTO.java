package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MarcaRequestDTO(
        @NotBlank(message = "O nome da marca é obrigatório") @Size(min = 2, max = 50, message = "O nome da marca deve ter entre 2 e 50 caracteres") String nome,

        @NotBlank(message = "A descrição da marca é obrigatória") @Size(min = 10, max = 255, message = "A descrição deve ter entre 10 e 255 caracteres") String descricao) {
}