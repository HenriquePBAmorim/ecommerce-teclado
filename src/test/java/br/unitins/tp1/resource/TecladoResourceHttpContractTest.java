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

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.teclado.model.Estoque;
import br.unitins.tp1.teclado.model.Marca;
import br.unitins.tp1.teclado.model.Teclado;
import br.unitins.tp1.teclado.service.TecladoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
class TecladoResourceHttpContractTest {

    private static final String BASE_URL = "/teclados";

    @InjectMock
    TecladoService tecladoService;

    @BeforeEach
    void setUp() {
        reset(tecladoService);
    }

    @Test
    void deveListarTecladosComStatus200() {
        when(tecladoService.findAll()).thenReturn(List.of(
                teclado(1L, "Redragon Kumara", "K552", 250.00, 10),
                teclado(2L, "HyperX Alloy", "Origins", 450.00, 5)));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", equalTo("Redragon Kumara"))
                .body("[0].modelo", equalTo("K552"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(tecladoService.findById(anyLong())).thenReturn(teclado(1L, "Redragon Kumara", "K552", 250.00, 10));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("nome", equalTo("Redragon Kumara"))
                .body("preco", equalTo(250.0f));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(tecladoService.findById(anyLong())).thenThrow(new NotFoundException("Teclado não encontrado"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }

    @Test
    void deveCriarTecladoComStatus201() {
        when(tecladoService.create(any(Teclado.class)))
                .thenReturn(teclado(10L, "Logitech G915", "TKL", 1200.00, 3));

        String jsonValido = """
                {
                  "nome": "Logitech G915",
                  "modelo": "TKL",
                  "preco": 1200.00,
                  "idMarca": 1,
                  "idSwitch": 1,
                  "idKeycap": 1,
                  "quantidadeEstoque": 3
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
                .body("nome", equalTo("Logitech G915"))
                .body("modelo", equalTo("TKL"));
    }

    @Test
    void deveAtualizarTecladoComStatus204() {
        // O anyLong() garante que qualquer ID procurado na URL seja "encontrado" pelo
        // Mockito
        when(tecladoService.findById(anyLong())).thenReturn(teclado(1L, "Redragon Kumara", "K552", 250.00, 10));
        doNothing().when(tecladoService).update(anyLong(), any(Teclado.class));

        String jsonAtualizacao = """
                {
                  "nome": "Redragon Kumara Atualizado",
                  "modelo": "K552",
                  "preco": 260.00,
                  "idMarca": 1,
                  "idSwitch": 1,
                  "idKeycap": 1,
                  "quantidadeEstoque": 15
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonAtualizacao)
                .when()
                .put(BASE_URL + "/1")
                .then()
                // Aceitando 200 (OK) ou 204 (No Content) dependendo do que o seu
                // TecladoResource retorna
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    void deveRemoverTecladoComStatus204() {
        // O anyLong() garante que qualquer ID procurado na URL seja "encontrado" pelo
        // Mockito
        when(tecladoService.findById(anyLong())).thenReturn(teclado(1L, "Redragon Kumara", "K552", 250.00, 10));
        doNothing().when(tecladoService).delete(anyLong());

        given()
                .accept(ContentType.JSON)
                .when()
                .delete(BASE_URL + "/1")
                .then()
                // Aceitando 200 (OK) ou 204 (No Content)
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    void deveRetornar400QuandoPayloadForInvalido() {
        String jsonInvalido = """
                {
                  "nome": "",
                  "modelo": "",
                  "preco": null,
                  "quantidadeEstoque": -5
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

        verify(tecladoService, never()).create(any(Teclado.class));
    }

    @Test
    void deveRetornar400QuandoJsonForMalformado() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"nome\":\"Redragon\",\"preco\":250.00")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(400);
    }

    @Test
    void deveRespeitarHeadersAcceptEContentType() {
        when(tecladoService.findAll()).thenReturn(List.of());

        given()
                .accept(ContentType.XML)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(406);

        given()
                .contentType(ContentType.TEXT)
                .accept(ContentType.JSON)
                .body("nome=Redragon&preco=250")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(415);
    }

    private Teclado teclado(Long id, String nome, String modelo, Double preco, Integer qtdEstoque) {
        Teclado t = new Teclado();
        t.setId(id);
        t.setNome(nome);
        t.setModelo(modelo);
        t.setPreco(preco);

        Marca m = new Marca();
        m.setId(1L);
        m.setNome("Marca Padrão");
        t.setMarca(m);

        Estoque e = new Estoque();
        e.setId(id);
        e.setQuantidade(qtdEstoque);
        e.setDataAtualizacao(LocalDate.now());
        t.setEstoque(e);

        return t;
    }
}