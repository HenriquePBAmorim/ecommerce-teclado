package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoRequestDTO(
        @NotNull(message = "O ID do teclado não pode ser nulo.")
        Long idTeclado,

        @NotNull(message = "A nota não pode ser nula.")
        @Min(value = 1, message = "A nota mínima é 1.")
        @Max(value = 5, message = "A nota máxima é 5.")
        Integer nota,

        String comentario
) {
}
