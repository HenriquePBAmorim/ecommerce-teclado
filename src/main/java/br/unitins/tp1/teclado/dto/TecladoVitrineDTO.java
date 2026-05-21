package br.unitins.tp1.teclado.dto;

import br.unitins.tp1.teclado.model.Teclado;

public record TecladoVitrineDTO(
    Long id,
    String nome,
    String modelo,
    Double preco,
    String marca
) {
    public static TecladoVitrineDTO valueOf(Teclado teclado) {
        return new TecladoVitrineDTO(
            teclado.getId(),
            teclado.getNome(), 
            teclado.getModelo(),
            teclado.getPreco(),
            teclado.getMarca() != null ? teclado.getMarca().getNome() : "Sem marca"
        );
    }
}
