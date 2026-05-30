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

import br.unitins.tp1.teclado.model.Estado;
import br.unitins.tp1.teclado.model.Regiao;
import br.unitins.tp1.teclado.service.EstadoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
class EstadoResourceHttpContractTest {

    private static final String BASE_URL = "/estados";

    @InjectMock
    EstadoService estadoService;

    @BeforeEach
    void setUp() {
        reset(estadoService);
    }

    @Test
    void deveListarEstadosComStatus200() {
        when(estadoService.findAll()).thenReturn(List.of(
                criarEstado(1L, "TO", "Tocantins", Regiao.NORTE),
                criarEstado(2L, "GO", "Goiás", Regiao.CENTRO_OESTE)));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].sigla", equalTo("TO"))
                .body("[0].nome", equalTo("Tocantins"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(estadoService.findById(anyLong())).thenReturn(criarEstado(1L, "TO", "Tocantins", Regiao.NORTE));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("sigla", equalTo("TO"))
                .body("nome", equalTo("Tocantins"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(estadoService.findById(anyLong())).thenThrow(new NotFoundException("Estado não encontrado"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }

    @Test
    void deveCriarEstadoComStatus201() {
        when(estadoService.create(any(Estado.class)))
                .thenReturn(criarEstado(10L, "SP", "São Paulo", Regiao.SUDESTE));

        String jsonValido = """
                {
                  "sigla": "SP",
                  "nome": "São Paulo",
                  "idRegiao": 4
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
                .body("sigla", equalTo("SP"))
                .body("nome", equalTo("São Paulo"));
    }

    @Test
    void deveAtualizarEstadoComStatus204() {
        when(estadoService.findById(anyLong())).thenReturn(criarEstado(1L, "TO", "Tocantins", Regiao.NORTE));
        doNothing().when(estadoService).update(anyLong(), any(Estado.class));

        String jsonAtualizacao = """
                {
                  "sigla": "TO",
                  "nome": "Tocantins Atualizado",
                  "idRegiao": 3
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
    void deveRemoverEstadoComStatus204() {
        when(estadoService.findById(anyLong())).thenReturn(criarEstado(1L, "TO", "Tocantins", Regiao.NORTE));
        doNothing().when(estadoService).delete(anyLong());

        given()
                .accept(ContentType.JSON)
                .when()
                .delete(BASE_URL + "/1")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    void deveRetornar400QuandoPayloadForInvalido() {
        String jsonInvalido = """
                {
                  "sigla": "",
                  "nome": "",
                  "idRegiao": null
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
                .body("code", equalTo("400"))
                .body("errors", hasSize(greaterThanOrEqualTo(1)));

        verify(estadoService, never()).create(any(Estado.class));
    }

    private Estado criarEstado(Long id, String sigla, String nome, Regiao regiao) {
        Estado e = new Estado();
        e.setId(id);
        e.setSigla(sigla);
        e.setNome(nome);
        e.setRegiao(regiao);
        return e;
    }
}