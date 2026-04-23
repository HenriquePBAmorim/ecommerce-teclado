package br.unitins.tp1.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.teclado.model.Switch;
import br.unitins.tp1.teclado.service.SwitchService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
class SwitchResourceHttpContractTest {

    private static final String BASE_URL = "/switches";

    @InjectMock
    SwitchService switchService;

    @BeforeEach
    void setUp() {
        reset(switchService);
    }

    @Test
    void deveListarSwitchesComStatus200() {
        when(switchService.findAll()).thenReturn(List.of(
                criarSwitch(1L, "Outemu Blue", "Outemu", 60.0),
                criarSwitch(2L, "Cherry MX Red", "Cherry GmbH", 45.0)));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", equalTo("Outemu Blue"))
                .body("[1].nome", equalTo("Cherry MX Red"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(switchService.findById(anyLong())).thenReturn(criarSwitch(1L, "Outemu Blue", "Outemu", 60.0));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("nome", equalTo("Outemu Blue"))
                .body("fabricante", equalTo("Outemu"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(switchService.findById(anyLong())).thenThrow(new NotFoundException("Switch não encontrado"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }

    @Test
    void deveCriarSwitchComStatus201() {
        when(switchService.create(any(Switch.class)))
                .thenReturn(criarSwitch(10L, "Razer Green", "Razer", 50.0));

        String jsonValido = """
                {
                  "nome": "Razer Green",
                  "fabricante": "Razer",
                  "idTipoSwitch": 1,
                  "forcaAtuacao": 50.0
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonValido)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", equalTo(10))
                .body("nome", equalTo("Razer Green"))
                .body("fabricante", equalTo("Razer"));
    }

    @Test
    void deveAtualizarSwitchComStatus204() {
        when(switchService.findById(anyLong())).thenReturn(criarSwitch(1L, "Outemu Blue", "Outemu", 60.0));
        doNothing().when(switchService).update(anyLong(), any(Switch.class));

        String jsonAtualizacao = """
                {
                  "nome": "Outemu Brown",
                  "fabricante": "Outemu",
                  "idTipoSwitch": 2,
                  "forcaAtuacao": 55.0
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonAtualizacao)
                .when()
                .put(BASE_URL + "/1")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    void deveRemoverSwitchComStatus204() {
        when(switchService.findById(anyLong())).thenReturn(criarSwitch(1L, "Outemu Blue", "Outemu", 60.0));
        doNothing().when(switchService).delete(anyLong());

        given()
                .accept(ContentType.JSON)
                .when()
                .delete(BASE_URL + "/1")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    void deveRetornar400QuandoPayloadForInvalido() {
        // Simula envio de Switch sem os dados obrigatórios
        String jsonInvalido = """
                {
                  "nome": "",
                  "fabricante": "",
                  "forcaAtuacao": null
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonInvalido)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("title", equalTo("Constraint Violation"))
                .body("status", equalTo(400))
                .body("violations", hasSize(greaterThanOrEqualTo(1)));

        verify(switchService, never()).create(any(Switch.class));
    }

    // Método auxiliar para criar instâncias mockadas de Switch
    private Switch criarSwitch(Long id, String nome, String fabricante, Double forcaAtuacao) {
        Switch s = new Switch();
        s.setId(id);
        s.setNome(nome);
        s.setFabricante(fabricante);
        s.setForcaAtuacao(forcaAtuacao);
        return s;
    }
}