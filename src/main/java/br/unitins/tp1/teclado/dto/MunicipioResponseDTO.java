package br.unitins.tp1.teclado.dto;

public record MunicipioResponseDTO(
        Long id,
        String nome,
        Long estadoId,
        String estadoNome) {
}