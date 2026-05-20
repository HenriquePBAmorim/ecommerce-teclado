package br.unitins.tp1.teclado.repository;

import br.unitins.tp1.teclado.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    // Busca todos os endereços de um usuário específico
    public PanacheQuery<Endereco> findByUsuario(Long idUsuario) {
        return find("usuario.id", idUsuario);
    }
}