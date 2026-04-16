package br.unitins.tp1.teclado.mapper;

import br.unitins.tp1.teclado.dto.TecladoRequestDTO;
import br.unitins.tp1.teclado.dto.TecladoResponseDTO;
import br.unitins.tp1.teclado.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TecladoMapper {

    public static Teclado toEntity(TecladoRequestDTO dto) {
        if (dto == null)
            return null;

        Teclado teclado = new Teclado();
        teclado.setNome(dto.nome());
        teclado.setPreco(dto.preco());
        teclado.setModelo(dto.modelo());
        teclado.setIdioma(dto.idioma());
        teclado.setComFio(dto.comFio());
        teclado.setIluminacaoRgb(dto.iluminacaoRgb());

        Marca marca = new Marca();
        marca.setId(dto.idMarca());
        teclado.setMarca(marca);

        Switch switchTeclado = new Switch();
        switchTeclado.setId(dto.idSwitch());
        teclado.setSwitchTeclado(switchTeclado);

        Keycap keycap = new Keycap();
        keycap.setId(dto.idKeycap());
        teclado.setKeycap(keycap);

        if (dto.idCategorias() != null) {
            teclado.setCategorias(dto.idCategorias().stream().map(id -> {
                Categoria cat = new Categoria();
                cat.setId(id);
                return cat;
            }).collect(Collectors.toList()));
        }

        Estoque estoque = new Estoque();
        estoque.setQuantidade(dto.quantidadeEstoque());
        estoque.setDataAtualizacao(LocalDate.now());
        teclado.setEstoque(estoque);

        return teclado;
    }

    public static TecladoResponseDTO toResponseDTO(Teclado teclado) {
        if (teclado == null)
            return null;

        return new TecladoResponseDTO(
                teclado.getId(),
                teclado.getNome(),
                teclado.getModelo(),
                teclado.getPreco(),
                teclado.getEstoque() != null ? teclado.getEstoque().getQuantidade() : 0,
                teclado.getMarca() != null ? teclado.getMarca().getNome() : null,
                teclado.getSwitchTeclado() != null ? teclado.getSwitchTeclado().getNome() : null,
                teclado.getKeycap() != null ? teclado.getKeycap().getNome() : null,
                teclado.getCategorias() != null
                        ? teclado.getCategorias().stream().map(Categoria::getNome).collect(Collectors.toList())
                        : new ArrayList<>());
    }
}