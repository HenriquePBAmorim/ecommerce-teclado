package br.unitins.tp1.teclado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDTO(
        @NotBlank(message = "O logradouro (rua, avenida) é obrigatório") @Size(min = 3, max = 150, message = "O logradouro deve conter entre 3 e 150 caracteres") String logradouro,

        @NotBlank(message = "O número é obrigatório") @Size(max = 10, message = "O número deve conter no máximo 10 caracteres") String numero,

        String complemento,

        @NotBlank(message = "O bairro é obrigatório") @Size(min = 2, max = 50, message = "O bairro deve conter entre 2 e 50 caracteres") String bairro,

        @NotBlank(message = "O CEP é obrigatório") @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 00000-000") String cep,

        @NotNull(message = "O município (id) é obrigatório") Long idMunicipio) {
}