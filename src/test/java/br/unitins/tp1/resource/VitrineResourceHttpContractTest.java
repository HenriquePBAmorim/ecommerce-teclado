package br.unitins.tp1.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class VitrineResourceHttpContractTest {

    @Test
    public void deveRetornarTodosTecladosDaVitrineComStatus200() {
        given()
                .when().get("/vitrine/teclados")
                .then()
                .statusCode(200);
    }

    @Test
    public void deveRetornarDetalhesDoTecladoDaVitrineComStatus200() {
        given()
                .when().get("/vitrine/teclados/1")
                .then()
                .statusCode(200)
                .body("id", is(1));
    }
}
