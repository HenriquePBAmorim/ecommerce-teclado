package br.unitins.tp1.teclado.dto;

public record TecladoRequestDTO(
        String nome,
        Long idTipoSwitch,
        Long idFormato,
        Long idMarca,
        Double preco) {
}