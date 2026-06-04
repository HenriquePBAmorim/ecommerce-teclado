package br.unitins.tp1.teclado.service;

import java.util.List;

import br.unitins.tp1.teclado.dto.AvaliacaoRequestDTO;
import br.unitins.tp1.teclado.dto.AvaliacaoResponseDTO;

public interface AvaliacaoService {
    AvaliacaoResponseDTO insert(String login, AvaliacaoRequestDTO dto);

    List<AvaliacaoResponseDTO> findByTeclado(Long idTeclado);
}
