package br.unitins.tp1.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;
import br.unitins.tp1.teclado.repository.UsuarioRepository;
import br.unitins.tp1.teclado.model.Usuario;

@QuarkusTest
public class DebugResourceHttpContractTest {

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioRepository repo;

    @Test
    @TestSecurity(user = "joao", roles = "USER")
    public void debug() {
        System.out.println("====== DEBUG START ======");
        System.out.println("JWT NAME: " + jwt.getName());
        Usuario u = repo.findByLogin("joao").orElse(null);
        System.out.println("USUARIO JOAO: " + (u != null ? u.getLogin() : "null"));
        System.out.println("====== DEBUG END ======");
    }
}
