package br.unitins.tp1.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class RegiaoResourceHttpContractTest {

    @Test
    @TestSecurity(user = "testUser", roles = { "ADMIN", "USER" })
    public void deveBuscarTodasRegioesComSucessoERetornar200() {
        given()
                .when().get("/regioes")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = { "ADMIN", "USER" })
    public void deveBuscarRegiaoPorIdComSucessoERetornar200() {
        given()
                .when().get("/regioes/1")
                .then()
                .statusCode(200);
    }
}
