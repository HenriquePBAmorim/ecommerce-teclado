package br.unitins.tp1.teclado.repository;

import br.unitins.tp1.teclado.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    // Método para o cliente ver o próprio histórico de compras
    public PanacheQuery<Pedido> findByUsuario(Long idUsuario) {
        return find("usuario.id", idUsuario);
    }
}