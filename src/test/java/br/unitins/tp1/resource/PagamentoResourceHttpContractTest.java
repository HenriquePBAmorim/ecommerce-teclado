package br.unitins.tp1.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class PagamentoResourceHttpContractTest {

    @Test
    @TestSecurity(user = "testUser", roles = {"ADMIN", "USER"})
    public void deveBuscarFormasPagamentoComSucessoERetornar200() {
        given()
            .when().get("/formas-pagamento")
            .then()
            .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"ADMIN", "USER"})
    public void deveRetornar405AoTentarCriarPagamentoViaPost() {
        // A API de Formas de Pagamento apenas possui a listagem dos tipos enum.
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body("{}")
            .when().post("/formas-pagamento")
            .then()
            .statusCode(405);
    }
}
