package br.unitins.tp1.teclado.mapper;

import br.unitins.tp1.teclado.dto.EnderecoRequestDTO;
import br.unitins.tp1.teclado.dto.EnderecoResponseDTO;
import br.unitins.tp1.teclado.model.Endereco;
import br.unitins.tp1.teclado.model.Municipio;

public class EnderecoMapper {

    public static Endereco toEntity(EnderecoRequestDTO dto) {
        if (dto == null)
            return null;

        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCep(dto.cep());
        endereco.setPrincipal(dto.principal() != null ? dto.principal() : false);

        Municipio municipio = new Municipio();
        municipio.setId(dto.idMunicipio());
        endereco.setMunicipio(municipio);

        return endereco;
    }

    public static EnderecoResponseDTO toResponseDTO(Endereco endereco) {
        if (endereco == null)
            return null;

        return new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCep(),
                MunicipioMapper.toResponseDTO(endereco.getMunicipio()),
                endereco.getPrincipal());
    }
}