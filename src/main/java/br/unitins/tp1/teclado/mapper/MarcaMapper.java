package br.unitins.tp1.teclado.mapper;

import br.unitins.tp1.teclado.dto.MarcaRequestDTO;
import br.unitins.tp1.teclado.dto.MarcaResponseDTO;
import br.unitins.tp1.teclado.model.Marca;

public class MarcaMapper {

    public static Marca toEntity(MarcaRequestDTO dto) {
        if (dto == null)
            return null;

        Marca marca = new Marca();
        marca.setNome(dto.nome());
        marca.setDescricao(dto.descricao());

        return marca;
    }

    public static MarcaResponseDTO toResponseDTO(Marca marca) {
        if (marca == null)
            return null;

        return new MarcaResponseDTO(
                marca.getId(),
                marca.getNome(),
                marca.getDescricao());
    }
}