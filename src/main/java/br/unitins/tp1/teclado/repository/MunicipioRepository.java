package br.unitins.tp1.teclado.repository;

import br.unitins.tp1.teclado.model.Municipio;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MunicipioRepository implements PanacheRepository<Municipio> {
    public PanacheQuery<Municipio> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1)", "%" + nome + "%");
    }

    public PanacheQuery<Municipio> findByEstado(Long idEstado) {
        return find("estado.id", idEstado);
    }
}