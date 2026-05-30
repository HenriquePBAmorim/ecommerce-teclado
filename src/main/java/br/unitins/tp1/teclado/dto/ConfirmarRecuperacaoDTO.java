package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ConfirmarRecuperacaoDTO(
    @NotBlank(message = "O e-mail é obrigatório") @Email(message = "E-mail inválido") String email,
    @NotBlank(message = "O código é obrigatório") String codigo,
    @NotBlank(message = "A nova senha é obrigatória") String novaSenha
) {
}
