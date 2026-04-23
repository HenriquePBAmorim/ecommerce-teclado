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

import br.unitins.tp1.teclado.model.Categoria;
import br.unitins.tp1.teclado.service.CategoriaService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
class CategoriaResourceHttpContractTest {

    private static final String BASE_URL = "/categorias";

    @InjectMock
    CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        reset(categoriaService);
    }

    @Test
    void deveListarCategoriasComStatus200() {
        when(categoriaService.findAll()).thenReturn(List.of(
                categoria(1L, "Gamer", "Foco em performance"),
                categoria(2L, "Sem Fio", "Conexão via Bluetooth")));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", equalTo("Gamer"))
                .body("[1].nome", equalTo("Sem Fio"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(categoriaService.findById(anyLong())).thenReturn(categoria(1L, "Gamer", "Foco em performance"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("nome", equalTo("Gamer"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(categoriaService.findById(anyLong())).thenThrow(new NotFoundException("Categoria não encontrada"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }

    @Test
    void deveCriarCategoriaComStatus201() {
        when(categoriaService.create(any(Categoria.class)))
                .thenReturn(categoria(10L, "Ergonômico", "Foco em saúde ocupacional"));

        String jsonValido = """
                {
                  "nome": "Ergonômico",
                  "descricao": "Foco em saúde ocupacional"
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
                .body("nome", equalTo("Ergonômico"));
    }

    @Test
    void deveAtualizarCategoriaComStatus204() {
        when(categoriaService.findById(anyLong())).thenReturn(categoria(1L, "Gamer", "Foco em performance"));
        doNothing().when(categoriaService).update(anyLong(), any(Categoria.class));

        String jsonAtualizacao = """
                {
                  "nome": "Gamer Pro",
                  "descricao": "Alta performance e RGB"
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
    void deveRemoverCategoriaComStatus204() {
        when(categoriaService.findById(anyLong())).thenReturn(categoria(1L, "Gamer", "Foco em performance"));
        doNothing().when(categoriaService).delete(anyLong());

        given()
                .accept(ContentType.JSON)
                .when()
                .delete(BASE_URL + "/1")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    void deveRetornar400QuandoPayloadForInvalido() {
        // Simula envio de Categoria sem o nome, que deve estar anotado com @NotBlank no
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
                .body("title", equalTo("Constraint Violation"))
                .body("status", equalTo(400))
                .body("violations", hasSize(greaterThanOrEqualTo(1)));

        verify(categoriaService, never()).create(any(Categoria.class));
    }

    // Método auxiliar para criar instâncias mockadas de Categoria
    private Categoria categoria(Long id, String nome, String descricao) {
        Categoria c = new Categoria();
        c.setId(id);
        c.setNome(nome);
        c.setDescricao(descricao);
        return c;
    }
}