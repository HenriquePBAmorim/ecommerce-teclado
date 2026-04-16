package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MunicipioRequestDTO(
                @NotBlank(message = "O nome do município é obrigatório") @Size(min = 2, max = 100, message = "O nome do município deve ter entre 2 e 100 caracteres.") String nome,

                @NotNull(message = "O estado (id) é obrigatório") Long idEstado) {
}