package br.unitins.tp1.teclado.mapper;

import br.unitins.tp1.teclado.dto.TecladoRequestDTO;
import br.unitins.tp1.teclado.dto.TecladoResponseDTO;
import br.unitins.tp1.teclado.model.Formato;
import br.unitins.tp1.teclado.model.Marca;
import br.unitins.tp1.teclado.model.Teclado;
import br.unitins.tp1.teclado.model.TipoSwitch;

public class TecladoMapper {

    public static Teclado toEntity(TecladoRequestDTO dto) {
        if (dto == null)
            return null;

        Teclado teclado = new Teclado();
        teclado.setNome(dto.nome());
        teclado.setTipoSwitch(TipoSwitch.valueOf(dto.idTipoSwitch()));
        teclado.setFormato(Formato.valueOf(dto.idFormato()));
        teclado.setPreco(dto.preco());

        // Cria uma referência da Marca apenas com o ID para salvar no banco
        if (dto.idMarca() != null) {
            Marca marca = new Marca();
            marca.setId(dto.idMarca());
            teclado.setMarca(marca);
        }

        return teclado;
    }

    public static TecladoResponseDTO toResponseDTO(Teclado teclado) {
        if (teclado == null)
            return null;

        return new TecladoResponseDTO(
                teclado.getId(),
                teclado.getNome(),
                teclado.getTipoSwitch(),
                teclado.getFormato(),
                MarcaMapper.toResponseDTO(teclado.getMarca()),
                teclado.getPreco());
    }
}