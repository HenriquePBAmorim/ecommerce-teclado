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
import br.unitins.tp1.teclado.model.Municipio;
import br.unitins.tp1.teclado.service.MunicipioService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
class MunicipioResourceHttpContractTest {

    private static final String BASE_URL = "/municipios";

    @InjectMock
    MunicipioService municipioService;

    @BeforeEach
    void setUp() {
        reset(municipioService);
    }

    @Test
    void deveListarMunicipiosComStatus200() {
        when(municipioService.findAll()).thenReturn(List.of(
                criarMunicipio(1L, "Palmas", 1L, "Tocantins"),
                criarMunicipio(2L, "Goiânia", 2L, "Goiás")));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", equalTo("Palmas"))
                .body("[1].nome", equalTo("Goiânia"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(municipioService.findById(anyLong())).thenReturn(criarMunicipio(1L, "Palmas", 1L, "Tocantins"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("nome", equalTo("Palmas"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(municipioService.findById(anyLong())).thenThrow(new NotFoundException("Município não encontrado"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }

    @Test
    void deveCriarMunicipioComStatus201() {
        when(municipioService.create(any(Municipio.class)))
                .thenReturn(criarMunicipio(10L, "Campinas", 3L, "São Paulo"));

        String jsonValido = """
                {
                  "nome": "Campinas",
                  "idEstado": 3
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonValido)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201)))
                .contentType(ContentType.JSON)
                .body("id", equalTo(10))
                .body("nome", equalTo("Campinas"));
    }

    @Test
    void deveAtualizarMunicipioComStatus204() {
        when(municipioService.findById(anyLong())).thenReturn(criarMunicipio(1L, "Palmas", 1L, "Tocantins"));
        doNothing().when(municipioService).update(anyLong(), any(Municipio.class));

        String jsonAtualizacao = """
                {
                  "nome": "Palmas Atualizado",
                  "idEstado": 1
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
    void deveRemoverMunicipioComStatus204() {
        when(municipioService.findById(anyLong())).thenReturn(criarMunicipio(1L, "Palmas", 1L, "Tocantins"));
        doNothing().when(municipioService).delete(anyLong());

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
                  "nome": "",
                  "idEstado": null
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

        verify(municipioService, never()).create(any(Municipio.class));
    }

    private Municipio criarMunicipio(Long id, String nome, Long idEstado, String nomeEstado) {
        Municipio m = new Municipio();
        m.setId(id);
        m.setNome(nome);

        Estado e = new Estado();
        e.setId(idEstado);
        e.setNome(nomeEstado);
        m.setEstado(e);

        return m;
    }
}