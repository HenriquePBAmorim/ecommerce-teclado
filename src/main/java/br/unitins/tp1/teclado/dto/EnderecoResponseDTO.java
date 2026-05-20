package br.unitins.tp1.teclado.dto;

public record EnderecoResponseDTO(
        Long id,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cep,
        MunicipioResponseDTO municipio) {
}