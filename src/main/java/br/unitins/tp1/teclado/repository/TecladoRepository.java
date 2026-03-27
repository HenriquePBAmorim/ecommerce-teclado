package br.unitins.tp1.teclado.repository;

import br.unitins.tp1.teclado.model.Teclado;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TecladoRepository implements PanacheRepository<Teclado> {
    public PanacheQuery<Teclado> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1)", "%" + nome + "%");
    }
}