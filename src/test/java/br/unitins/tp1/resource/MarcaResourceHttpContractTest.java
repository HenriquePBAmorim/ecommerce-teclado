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

import br.unitins.tp1.teclado.model.Marca;
import br.unitins.tp1.teclado.service.MarcaService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
class MarcaResourceHttpContractTest {

    private static final String BASE_URL = "/marcas";

    @InjectMock
    MarcaService marcaService;

    @BeforeEach
    void setUp() {
        reset(marcaService);
    }

    @Test
    void deveListarMarcasComStatus200() {
        when(marcaService.findAll()).thenReturn(List.of(
                marca(1L, "Redragon", "Marca chinesa gamer"),
                marca(2L, "HyperX", "Marca americana gamer")));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", equalTo("Redragon"))
                .body("[1].nome", equalTo("HyperX"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(marcaService.findById(anyLong())).thenReturn(marca(1L, "Redragon", "Marca chinesa gamer"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("nome", equalTo("Redragon"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(marcaService.findById(anyLong())).thenThrow(new NotFoundException("Marca não encontrada"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }

    @Test
    void deveCriarMarcaComStatus201() {
        when(marcaService.create(any(Marca.class)))
                .thenReturn(marca(10L, "Logitech", "Marca suíça gamer"));

        String jsonValido = """
                {
                  "nome": "Logitech",
                  "descricao": "Marca suíça gamer"
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
                .body("nome", equalTo("Logitech"));
    }

    @Test
    void deveAtualizarMarcaComStatus204() {
        when(marcaService.findById(anyLong())).thenReturn(marca(1L, "Redragon", "Marca chinesa gamer"));
        doNothing().when(marcaService).update(anyLong(), any(Marca.class));

        String jsonAtualizacao = """
                {
                  "nome": "Redragon Atualizada",
                  "descricao": "Nova descrição"
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
    void deveRemoverMarcaComStatus204() {
        when(marcaService.findById(anyLong())).thenReturn(marca(1L, "Redragon", "Marca chinesa gamer"));
        doNothing().when(marcaService).delete(anyLong());

        given()
                .accept(ContentType.JSON)
                .when()
                .delete(BASE_URL + "/1")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    void deveRetornar400QuandoPayloadForInvalido() {
        // Simula envio de Marca sem o nome, que deve estar anotado com @NotBlank no
        // RequestDTO
        String jsonInvalido = """
                {
                  "nome": "",
                  "descricao": "Falta o nome"
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

        verify(marcaService, never()).create(any(Marca.class));
    }

    // Método auxiliar para criar instâncias mockadas de Marca
    private Marca marca(Long id, String nome, String descricao) {
        Marca m = new Marca();
        m.setId(id);
        m.setNome(nome);
        m.setDescricao(descricao);
        return m;
    }
}