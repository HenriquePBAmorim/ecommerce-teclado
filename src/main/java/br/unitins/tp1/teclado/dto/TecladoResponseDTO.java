package br.unitins.tp1.teclado.dto;

import br.unitins.tp1.teclado.model.Formato;
import br.unitins.tp1.teclado.model.TipoSwitch;

public record TecladoResponseDTO(
        Long id,
        String nome,
        TipoSwitch tipoSwitch,
        Formato formato,
        MarcaResponseDTO marca,
        Double preco) {
}