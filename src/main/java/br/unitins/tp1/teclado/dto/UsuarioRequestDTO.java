package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank(message = "O nome é obrigatório.") String nome,

        @NotBlank(message = "O login é obrigatório.") String login,

        @NotBlank(message = "A senha é obrigatória.") @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.") String senha) {
}