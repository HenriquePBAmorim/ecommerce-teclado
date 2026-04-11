package br.unitins.tp1.teclado.dto;

import br.unitins.tp1.teclado.model.PerfilKeycap;

public record KeycapResponseDTO(
        Long id,
        String nome,
        String material,
        String cor,
        PerfilKeycap perfil) {
}