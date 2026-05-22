package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.NotBlank;

public record CartaoCreditoRequestDTO(
    @NotBlank(message = "Número do cartão é obrigatório.")
    String numeroCartao,
    
    @NotBlank(message = "Nome do titular é obrigatório.")
    String nomeTitular,
    
    @NotBlank(message = "CPF do titular é obrigatório.")
    String cpfTitular,
    
    @NotBlank(message = "Bandeira do cartão é obrigatória.")
    String bandeira
) {
}
