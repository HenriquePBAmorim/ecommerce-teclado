package br.unitins.tp1.teclado.dto;

import br.unitins.tp1.teclado.model.TipoSwitch;

public record SwitchResponseDTO(
        Long id,
        String nome,
        String fabricante,
        TipoSwitch tipo,
        Double forcaAtuacao) {
}