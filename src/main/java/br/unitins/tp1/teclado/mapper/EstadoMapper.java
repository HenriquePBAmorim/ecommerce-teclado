package br.unitins.tp1.teclado.mapper;

import br.unitins.tp1.teclado.dto.EstadoRequestDTO;
import br.unitins.tp1.teclado.dto.EstadoResponseDTO;
import br.unitins.tp1.teclado.model.Estado;
import br.unitins.tp1.teclado.model.Regiao;

public class EstadoMapper {

    public static Estado toEntity(EstadoRequestDTO dto) {
        if (dto == null)
            return null;

        Estado estado = new Estado();
        estado.setNome(dto.nome());
        estado.setSigla(dto.sigla());
        estado.setRegiao(Regiao.valueOf(dto.idRegiao()));

        return estado;
    }

    public static EstadoResponseDTO toResponseDTO(Estado estado) {
        if (estado == null)
            return null;

        return new EstadoResponseDTO(
                estado.getId(),
                estado.getSigla(),
                estado.getNome(),
                estado.getRegiao());
    }
}