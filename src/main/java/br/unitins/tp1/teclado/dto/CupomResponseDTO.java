package br.unitins.tp1.teclado.dto;

import br.unitins.tp1.teclado.model.Cupom;

public record CupomResponseDTO(
    Long id,
    String codigo,
    Double percentualDesconto,
    Boolean ativo
) {
    public CupomResponseDTO(Cupom cupom) {
        this(cupom.getId(), cupom.getCodigo(), cupom.getPercentualDesconto(), cupom.getAtivo());
    }
}
