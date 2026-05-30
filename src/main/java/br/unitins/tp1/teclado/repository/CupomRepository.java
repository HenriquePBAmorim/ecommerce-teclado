package br.unitins.tp1.teclado.repository;

import br.unitins.tp1.teclado.model.Cupom;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CupomRepository implements PanacheRepository<Cupom> {

    public Cupom findByCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) return null;
        return find("codigo", codigo.trim().toUpperCase()).firstResult();
    }
}
