package br.unitins.tp1.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class PedidoResourceHttpContractTest {

    @io.quarkus.test.InjectMock
    org.eclipse.microprofile.jwt.JsonWebToken jwt;

    @org.junit.jupiter.api.BeforeEach
    public void setup() {
        org.mockito.Mockito.when(jwt.getName()).thenReturn("joao");
    }

    @Test
    @TestSecurity(user = "admin", roles = {"ADMIN"})
    public void deveBuscarPedidosComSucessoERetornar200() {
        given()
            .when().get("/pedidos")
            .then()
            .statusCode(200);
    }

    @Test
    @TestSecurity(user = "joao", roles = {"USER"})
    public void deveCriarPedidoComSucessoERetornar201() {
        Map<String, Object> item = Map.of(
            "idTeclado", 1,
            "quantidade", 1
        );

        Map<String, Object> dto = Map.of(
            "idEnderecoEntrega", 1, // Assume-se que o usuário joao tem o endereço 1 associado a ele caso validem. Senão ignora.
            "idFormaPagamento", 1,  // Exemplo de Forma de Pagamento
            "itens", List.of(item)
        );

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
            .when().post("/pedidos")
            .then()
            .statusCode(201)
            .body("id", notNullValue());
    }
}
