package br.unitins.tp1.teclado.dto;

import br.unitins.tp1.teclado.model.Teclado;

public record TecladoVitrineDTO(
    Long id,
    String nome,
    String modelo,
    Double preco,
    String nomeMarca,
    String nomeSwitch,
    String nomeKeycap,
    Boolean emEstoque
) {
    public static TecladoVitrineDTO toDTO(Teclado teclado) {
        return new TecladoVitrineDTO(
            teclado.getId(),
            teclado.getNome(), 
            teclado.getModelo(),
            teclado.getPreco(),
            teclado.getMarca() != null ? teclado.getMarca().getNome() : "Sem marca",
            teclado.getSwitchTeclado() != null ? teclado.getSwitchTeclado().getNome() : "Genérico",
            teclado.getKeycap() != null ? teclado.getKeycap().getNome() : "Padrão",
            teclado.getEstoque() != null && teclado.getEstoque().getQuantidade() > 0
        );
    }
}
