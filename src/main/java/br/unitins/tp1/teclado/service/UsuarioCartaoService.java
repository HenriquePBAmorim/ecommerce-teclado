package br.unitins.tp1.teclado.service;

import br.unitins.tp1.teclado.dto.CartaoCreditoRequestDTO;
import br.unitins.tp1.teclado.dto.CartaoCreditoResponseDTO;

import java.util.List;

public interface UsuarioCartaoService {
    CartaoCreditoResponseDTO adicionarCartao(String login, CartaoCreditoRequestDTO dto);
    
    List<CartaoCreditoResponseDTO> listarCartoes(String login);
    
    void desativarCartao(String login, Long idCartao);
}
