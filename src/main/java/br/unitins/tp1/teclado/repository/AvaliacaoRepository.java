package br.unitins.tp1.teclado.repository;

import java.util.List;

import br.unitins.tp1.teclado.model.Avaliacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AvaliacaoRepository implements PanacheRepository<Avaliacao> {

    public List<Avaliacao> findByTeclado(Long idTeclado) {
        return find("teclado.id = ?1", idTeclado).list();
    }

    public Avaliacao findByUsuarioETeclado(Long idUsuario, Long idTeclado) {
        return find("usuario.id = ?1 and teclado.id = ?2", idUsuario, idTeclado).firstResult();
    }
}
