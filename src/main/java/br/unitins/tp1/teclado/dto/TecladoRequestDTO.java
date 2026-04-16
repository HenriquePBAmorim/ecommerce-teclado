package br.unitins.tp1.teclado.dto;

import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TecladoRequestDTO(
                @NotBlank(message = "O nome do teclado é obrigatório") @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres") String nome,

                @NotBlank(message = "O modelo é obrigatório") String modelo,

                @NotNull(message = "O preço é obrigatório") @Min(value = 1, message = "O preço deve ser maior que zero") Double preco,

                String idioma,
                Boolean comFio,
                Boolean iluminacaoRgb,

                @NotNull(message = "A marca é obrigatória") Long idMarca,

                @NotNull(message = "O switch é obrigatório") Long idSwitch,

                @NotNull(message = "A keycap é obrigatória") Long idKeycap,

                List<Long> idCategorias,

                @NotNull(message = "A quantidade inicial em estoque é obrigatória") @Min(value = 0, message = "O estoque não pode ser negativo") Integer quantidadeEstoque) {
}