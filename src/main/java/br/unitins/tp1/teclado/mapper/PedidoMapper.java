package br.unitins.tp1.teclado.mapper;

import java.util.List;
import br.unitins.tp1.teclado.dto.ItemPedidoResponseDTO;
import br.unitins.tp1.teclado.dto.PedidoResponseDTO;
import br.unitins.tp1.teclado.model.Pedido;

public class PedidoMapper {

    public static PedidoResponseDTO toResponseDTO(Pedido pedido) {
        if (pedido == null)
            return null;

        List<ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream()
                .map(item -> new ItemPedidoResponseDTO(
                        item.getTeclado().getId(),
                        item.getTeclado().getNome(),
                        item.getQuantidade(),
                        item.getPreco()))
                .toList();

        List<br.unitins.tp1.teclado.dto.HistoricoPedidoResponseDTO> historicoDTO = pedido.getHistorico() != null ? 
                pedido.getHistorico().stream()
                .map(h -> new br.unitins.tp1.teclado.dto.HistoricoPedidoResponseDTO(
                        h.getId(),
                        h.getStatusAnterior(),
                        h.getStatusNovo(),
                        h.getDataHora(),
                        h.getUsuarioResponsavel()
                )).toList() : new java.util.ArrayList<>();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getStatus(),
                pedido.getDataHora(),
                pedido.getValorTotal(),
                pedido.getValorDesconto(),
                pedido.getValorFrete(),
                pedido.getCupom() != null ? pedido.getCupom().getCodigo() : null,
                itensDTO,
                historicoDTO);
    }
}