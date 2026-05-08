package br.unitins.tp1.teclado.repository;

import java.util.Optional;
import br.unitins.tp1.teclado.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    // Retorna Optional para facilitar o tratamento de erro no AuthService
    public Optional<Usuario> findByLogin(String login) {
        return find("login", login).firstResultOptional();
    }
}