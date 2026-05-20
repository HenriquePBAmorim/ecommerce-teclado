package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.dto.EnderecoRequestDTO;
import br.unitins.tp1.teclado.model.Endereco;

public interface EnderecoService {
    List<Endereco> findByUsuario(Long idUsuario);

    Endereco findById(Long id);

    Endereco create(Long idUsuario, EnderecoRequestDTO dto);

    void update(Long id, EnderecoRequestDTO dto);

    void delete(Long id);
}