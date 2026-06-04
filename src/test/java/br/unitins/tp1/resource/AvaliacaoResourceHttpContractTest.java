package br.unitins.tp1.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.teclado.dto.AvaliacaoRequestDTO;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AvaliacaoResourceHttpContractTest {

    @Test
    @TestSecurity(user = "joao", roles = "USER")
    public void deveAvaliarProdutoComSucessoSeCompraVerificada() {
        AvaliacaoRequestDTO dto = new AvaliacaoRequestDTO(1L, 5, "Produto sensacional, valeu cada centavo!");

        given()
                .contentType("application/json")
                .body(dto)
                .when().post("/avaliacoes")
                .then()
                .statusCode(201)
                .body("mensagem", is("Avaliação registrada com sucesso!"));
    }

    @Test
    @TestSecurity(user = "joao", roles = "USER")
    public void deveRetornar403ForbiddenAoAvaliarProdutoNaoComprado() {
        // João não tem compra entregue do teclado 3
        AvaliacaoRequestDTO dto = new AvaliacaoRequestDTO(3L, 4, "Achei mediano.");

        given()
                .contentType("application/json")
                .body(dto)
                .when().post("/avaliacoes")
                .then()
                .statusCode(403);
    }

    @Test
    public void deveBuscarAvaliacoesDeUmTecladoPublicamente() {
        given()
                .when().get("/avaliacoes/teclado/1")
                .then()
                .statusCode(200);
    }
}
