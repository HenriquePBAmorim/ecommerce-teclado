package br.unitins.tp1.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class EnderecoResourceHttpContractTest {

    @io.quarkus.test.InjectMock
    org.eclipse.microprofile.jwt.JsonWebToken jwt;

    @org.junit.jupiter.api.BeforeEach
    public void setup() {
        org.mockito.Mockito.when(jwt.getName()).thenReturn("joao");
    }

    @Test
    @TestSecurity(user = "admin", roles = { "ADMIN" })
    public void deveBuscarEnderecosComSucessoERetornar200() {
        given()
                .when().get("/enderecos")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "joao", roles = { "USER" })
    public void deveCriarEnderecoComSucessoERetornar201() {
        Map<String, Object> dto = Map.of(
                "logradouro", "Rua das Flores",
                "numero", "123",
                "bairro", "Centro",
                "cep", "77000-000",
                "idMunicipio", 1);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().post("/enderecos")
                .then()
                .statusCode(201)
                .body("logradouro", is("Rua das Flores"));
    }
}
