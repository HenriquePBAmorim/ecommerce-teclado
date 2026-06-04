package br.unitins.tp1.teclado.dto;

import java.time.LocalDateTime;
import br.unitins.tp1.teclado.model.StatusPedido;

public record HistoricoPedidoResponseDTO(
        Long id,
        StatusPedido statusAnterior,
        StatusPedido statusNovo,
        LocalDateTime dataHora,
        String usuarioResponsavel) {
}
