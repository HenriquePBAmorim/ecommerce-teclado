package br.unitins.tp1.resource;

import br.unitins.tp1.teclado.dto.CadastroClienteDTO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UsuarioResourceHttpContractTest {

    @Test
    public void deveCriarUsuarioERetornar201Created() {
        CadastroClienteDTO dto = new CadastroClienteDTO("Mariazinha", "maria123", "maria@email.com", "senha123");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().post("/usuarios/cadastro")
                .then()
                .statusCode(201)
                .body("mensagem", is("Cadastro realizado com sucesso!"));
    }
}
