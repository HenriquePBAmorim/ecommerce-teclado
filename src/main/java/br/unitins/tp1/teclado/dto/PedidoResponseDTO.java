package br.unitins.tp1.teclado.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        LocalDateTime dataHora,
        Double valorTotal,
        Double valorDesconto,
        String codigoCupom,
        List<ItemPedidoResponseDTO> itens) {
}