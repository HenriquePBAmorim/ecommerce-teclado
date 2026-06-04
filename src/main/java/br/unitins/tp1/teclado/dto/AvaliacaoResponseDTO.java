package br.unitins.tp1.teclado.dto;

import java.time.LocalDateTime;

public record AvaliacaoResponseDTO(
        Integer nota,
        String comentario,
        LocalDateTime dataAvaliacao,
        String nomeUsuario
) {
}
