package br.unitins.tp1.teclado.repository;

import br.unitins.tp1.teclado.model.HistoricoPedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HistoricoPedidoRepository implements PanacheRepository<HistoricoPedido> {
}
