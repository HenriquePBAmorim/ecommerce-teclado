package br.unitins.tp1.teclado.mapper;

import br.unitins.tp1.teclado.dto.SwitchRequestDTO;
import br.unitins.tp1.teclado.dto.SwitchResponseDTO;
import br.unitins.tp1.teclado.model.Switch;
import br.unitins.tp1.teclado.model.TipoSwitch;

public class SwitchMapper {

    public static Switch toEntity(SwitchRequestDTO dto) {
        if (dto == null)
            return null;

        Switch sw = new Switch();
        sw.setNome(dto.nome());
        sw.setFabricante(dto.fabricante());
        sw.setTipo(TipoSwitch.valueOf(dto.idTipoSwitch()));
        sw.setForcaAtuacao(dto.forcaAtuacao());

        return sw;
    }

    public static SwitchResponseDTO toResponseDTO(Switch sw) {
        if (sw == null)
            return null;

        return new SwitchResponseDTO(
                sw.getId(),
                sw.getNome(),
                sw.getFabricante(),
                sw.getTipo(),
                sw.getForcaAtuacao());
    }
}