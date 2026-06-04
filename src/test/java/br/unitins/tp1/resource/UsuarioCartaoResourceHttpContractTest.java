package br.unitins.tp1.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.teclado.dto.CartaoCreditoRequestDTO;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UsuarioCartaoResourceHttpContractTest {

    @Test
    @TestSecurity(user = "joao", roles = "USER")
    public void deveAdicionarCartaoComSucesso() {
        CartaoCreditoRequestDTO dto = new CartaoCreditoRequestDTO(
                "9876543210987654",
                "JOAO SILVA",
                "11122233344",
                "VISA"
        );

        given()
                .contentType("application/json")
                .body(dto)
                .when().post("/meus-cartoes")
                .then()
                .statusCode(201);
    }

    @Test
    @TestSecurity(user = "joao", roles = "USER")
    public void deveListarCartoesDoUsuario() {
        given()
                .when().get("/meus-cartoes")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "joao", roles = "USER")
    public void deveDesativarCartaoComSucesso() {
        // Assume que o cartão 1 (que está no import.sql) pertence ao João e pode ser desativado
        given()
                .when().patch("/meus-cartoes/1/desativar")
                .then()
                .statusCode(200)
                .body("mensagem", is("Cartão desativado com sucesso!"));
    }
}
