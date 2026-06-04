package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.dto.PedidoRequestDTO;
import br.unitins.tp1.teclado.dto.PedidoResponseDTO;
import br.unitins.tp1.teclado.model.Pedido;

public interface PedidoService {
    Pedido create(Long idUsuario, PedidoRequestDTO dto);

    List<Pedido> findByUsuario(Long idUsuario);

    List<Pedido> findAll();

    Pedido findById(Long id);

    List<PedidoResponseDTO> meusPedidos(String login);

    void cancelarPedido(Long idPedido, String loginUsuario);
}