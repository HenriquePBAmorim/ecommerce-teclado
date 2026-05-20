package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.dto.PedidoRequestDTO;
import br.unitins.tp1.teclado.model.Pedido;

public interface PedidoService {
    Pedido create(Long idUsuario, PedidoRequestDTO dto);

    List<Pedido> findByUsuario(Long idUsuario);

    Pedido findById(Long id);
}