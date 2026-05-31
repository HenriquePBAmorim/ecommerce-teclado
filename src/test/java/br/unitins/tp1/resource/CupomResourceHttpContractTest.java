package br.unitins.tp1.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CupomResourceHttpContractTest {

    @Test
    @TestSecurity(user = "testUser", roles = {"ADMIN"})
    public void deveBuscarCupomComSucessoERetornar200() {
        given()
            .when().get("/cupons")
            .then()
            .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"ADMIN"})
    public void deveCriarCupomComSucessoERetornar201() {
        Map<String, Object> dto = Map.of(
            "codigo", "PROMO" + System.currentTimeMillis(), // Garante unicidade caso o cupom possua unique constraint
            "percentualDesconto", 10.0
        );

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
            .when().post("/cupons")
            .then()
            .statusCode(201)
            .body("id", notNullValue());
    }
}
