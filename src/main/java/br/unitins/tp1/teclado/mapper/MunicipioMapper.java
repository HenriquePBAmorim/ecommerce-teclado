package br.unitins.tp1.teclado.mapper;

import br.unitins.tp1.teclado.dto.MunicipioRequestDTO;
import br.unitins.tp1.teclado.dto.MunicipioResponseDTO;
import br.unitins.tp1.teclado.model.Estado;
import br.unitins.tp1.teclado.model.Municipio;

public class MunicipioMapper {

    public static Municipio toEntity(MunicipioRequestDTO dto) {
        if (dto == null)
            return null;

        Municipio municipio = new Municipio();
        municipio.setNome(dto.nome());

        Estado estado = new Estado();
        estado.setId(dto.idEstado());
        municipio.setEstado(estado);

        return municipio;
    }

    public static MunicipioResponseDTO toResponseDTO(Municipio municipio) {
        if (municipio == null)
            return null;

        return new MunicipioResponseDTO(
                municipio.getId(),
                municipio.getNome(),
                municipio.getEstado().getId(),
                municipio.getEstado().getNome());
    }
}