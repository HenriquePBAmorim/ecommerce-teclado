package br.unitins.tp1.teclado.mapper;

import br.unitins.tp1.teclado.dto.KeycapRequestDTO;
import br.unitins.tp1.teclado.dto.KeycapResponseDTO;
import br.unitins.tp1.teclado.model.Keycap;
import br.unitins.tp1.teclado.model.PerfilKeycap;

public class KeycapMapper {

    public static Keycap toEntity(KeycapRequestDTO dto) {
        if (dto == null)
            return null;

        Keycap keycap = new Keycap();
        keycap.setNome(dto.nome());
        keycap.setMaterial(dto.material());
        keycap.setCor(dto.cor());
        keycap.setPerfil(PerfilKeycap.valueOf(dto.idPerfil()));

        return keycap;
    }

    public static KeycapResponseDTO toResponseDTO(Keycap keycap) {
        if (keycap == null)
            return null;

        return new KeycapResponseDTO(
                keycap.getId(),
                keycap.getNome(),
                keycap.getMaterial(),
                keycap.getCor(),
                keycap.getPerfil());
    }
}