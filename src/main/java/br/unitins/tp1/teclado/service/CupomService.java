package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.dto.CupomRequestDTO;
import br.unitins.tp1.teclado.dto.CupomResponseDTO;

public interface CupomService {
    CupomResponseDTO criar(CupomRequestDTO dto);
    CupomResponseDTO atualizar(Long id, CupomRequestDTO dto);
    void desativar(Long id);
    List<CupomResponseDTO> listarTodos();
    CupomResponseDTO buscarPorCodigo(String codigo);
}
