package br.unitins.tp1.teclado.dto;

import br.unitins.tp1.teclado.model.Regiao;

public record EstadoResponseDTO(
        Long id,
        String sigla,
        String nome,
        Regiao regiao) {
}