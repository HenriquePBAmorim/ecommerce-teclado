package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record KeycapRequestDTO(
        @NotBlank(message = "O nome da keycap é obrigatório") @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres") String nome,

        @NotBlank(message = "O material da keycap é obrigatório (ex: ABS, PBT)") @Size(min = 2, max = 30, message = "O material deve ter entre 2 e 30 caracteres") String material,

        @NotBlank(message = "A cor da keycap é obrigatória") String cor,

        @NotNull(message = "O perfil da keycap é obrigatório") Long idPerfil) {
}