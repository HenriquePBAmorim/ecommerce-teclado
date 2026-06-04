package br.unitins.tp1.teclado.mapper;

import br.unitins.tp1.teclado.dto.AvaliacaoResponseDTO;
import br.unitins.tp1.teclado.model.Avaliacao;

public class AvaliacaoMapper {

    public static AvaliacaoResponseDTO toResponseDTO(Avaliacao avaliacao) {
        if (avaliacao == null) {
            return null;
        }

        return new AvaliacaoResponseDTO(
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getDataAvaliacao(),
                avaliacao.getUsuario() != null ? avaliacao.getUsuario().getNome() : "Usuário Anônimo"
        );
    }
}
