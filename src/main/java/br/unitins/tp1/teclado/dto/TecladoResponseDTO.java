package br.unitins.tp1.teclado.dto;

import java.util.List;

public record TecladoResponseDTO(
                Long id,
                String nome,
                String modelo,
                Double preco,
                Integer quantidadeEstoque,
                String nomeMarca,
                String nomeSwitch,
                String nomeKeycap,
                List<String> categorias) {
}