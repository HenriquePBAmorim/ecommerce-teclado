package br.unitins.tp1.teclado.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        br.unitins.tp1.teclado.model.StatusPedido status,
        LocalDateTime dataHora,
        Double valorTotal,
        Double valorDesconto,
        String codigoCupom,
        List<ItemPedidoResponseDTO> itens) {
}