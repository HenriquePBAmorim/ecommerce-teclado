package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastroClienteDTO(
    @NotBlank(message = "O nome é obrigatório.")
    String nome,

    @NotBlank(message = "O CPF é obrigatório.")
    String cpf,

    @NotBlank(message = "O login é obrigatório.")
    String login,

    @NotBlank(message = "A senha é obrigatória.")
    String senha
) {
}
