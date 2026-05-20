package br.unitins.tp1.teclado.dto;

public record ItemPedidoResponseDTO(
        Long idTeclado,
        String nomeTeclado,
        Integer quantidade,
        Double precoUnitario) {
}