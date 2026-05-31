package br.unitins.tp1.resource;

import br.unitins.tp1.teclado.dto.AuthRequestDTO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class AuthResourceHttpContractTest {

    @Test
    public void deveRetornarTokenAoFazerLoginComCredenciaisValidas() {
        // Credenciais baseadas no import.sql
        AuthRequestDTO dto = new AuthRequestDTO("joao", "123456");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }
}
