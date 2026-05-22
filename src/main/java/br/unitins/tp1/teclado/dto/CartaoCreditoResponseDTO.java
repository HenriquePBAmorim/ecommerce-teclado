package br.unitins.tp1.teclado.dto;

import br.unitins.tp1.teclado.model.CartaoCredito;

public record CartaoCreditoResponseDTO(
    Long id,
    String numeroCartao,
    String nomeTitular,
    String cpfTitular,
    String bandeira,
    Boolean ativo
) {
    public static CartaoCreditoResponseDTO valueOf(CartaoCredito cartao) {
        String cartaoMascarado = cartao.getNumeroCartao();
        if (cartaoMascarado != null && cartaoMascarado.length() >= 4) {
            cartaoMascarado = "**** **** **** " + cartaoMascarado.substring(cartaoMascarado.length() - 4);
        }

        return new CartaoCreditoResponseDTO(
            cartao.getId(),
            cartaoMascarado,
            cartao.getNomeTitular(),
            cartao.getCpfTitular(),
            cartao.getBandeira(),
            cartao.getAtivo()
        );
    }
}
