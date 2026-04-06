package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(
        @NotBlank(message = "O nome da categoria é obrigatório") @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres") String nome,

        @NotBlank(message = "A descrição da categoria é obrigatória") @Size(min = 5, max = 255, message = "A descrição deve ter entre 5 e 255 caracteres") String descricao) {
}