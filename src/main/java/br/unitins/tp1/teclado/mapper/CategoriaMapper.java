package br.unitins.tp1.teclado.mapper;

import br.unitins.tp1.teclado.dto.CategoriaRequestDTO;
import br.unitins.tp1.teclado.dto.CategoriaResponseDTO;
import br.unitins.tp1.teclado.model.Categoria;

public class CategoriaMapper {
    public static Categoria toEntity(CategoriaRequestDTO dto) {
        if (dto == null)
            return null;
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        return categoria;
    }

    public static CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        if (categoria == null)
            return null;
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao());
    }
}