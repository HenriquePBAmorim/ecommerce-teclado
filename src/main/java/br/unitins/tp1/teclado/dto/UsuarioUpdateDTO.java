package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateDTO(
    @NotBlank(message = "O nome é obrigatório")
    String nome,
    
    @NotBlank(message = "O CPF é obrigatório")
    String cpf
) {
}
