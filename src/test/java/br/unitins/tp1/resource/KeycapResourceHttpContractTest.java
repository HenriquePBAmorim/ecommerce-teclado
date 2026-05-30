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

import br.unitins.tp1.teclado.model.Keycap;
import br.unitins.tp1.teclado.service.KeycapService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
class KeycapResourceHttpContractTest {

    private static final String BASE_URL = "/keycaps";

    @InjectMock
    KeycapService keycapService;

    @BeforeEach
    void setUp() {
        reset(keycapService);
    }

    @Test
    void deveListarKeycapsComStatus200() {
        when(keycapService.findAll()).thenReturn(List.of(
                criarKeycap(1L, "HyperX Pudding", "PBT", "Preto"),
                criarKeycap(2L, "Razer Phantom", "ABS", "Branco")));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", equalTo("HyperX Pudding"))
                .body("[1].nome", equalTo("Razer Phantom"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(keycapService.findById(anyLong())).thenReturn(criarKeycap(1L, "HyperX Pudding", "PBT", "Preto"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("nome", equalTo("HyperX Pudding"))
                .body("material", equalTo("PBT"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(keycapService.findById(anyLong())).thenThrow(new NotFoundException("Keycap não encontrada"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }

    @Test
    void deveCriarKeycapComStatus201() {
        when(keycapService.create(any(Keycap.class)))
                .thenReturn(criarKeycap(10L, "Corsair Double-Shot", "PBT", "Preto"));

        String jsonValido = """
                {
                  "nome": "Corsair Double-Shot",
                  "material": "PBT",
                  "idPerfil": 1,
                  "cor": "Preto"
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
                .body("nome", equalTo("Corsair Double-Shot"))
                .body("cor", equalTo("Preto"));
    }

    @Test
    void deveAtualizarKeycapComStatus204() {
        when(keycapService.findById(anyLong())).thenReturn(criarKeycap(1L, "HyperX Pudding", "PBT", "Preto"));
        doNothing().when(keycapService).update(anyLong(), any(Keycap.class));

        String jsonAtualizacao = """
                {
                  "nome": "HyperX Pudding Atualizada",
                  "material": "PBT",
                  "idPerfil": 2,
                  "cor": "Branco"
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
    void deveRemoverKeycapComStatus204() {
        when(keycapService.findById(anyLong())).thenReturn(criarKeycap(1L, "HyperX Pudding", "PBT", "Preto"));
        doNothing().when(keycapService).delete(anyLong());

        given()
                .accept(ContentType.JSON)
                .when()
                .delete(BASE_URL + "/1")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    void deveRetornar400QuandoPayloadForInvalido() {
        // Simula envio de Keycap sem dados obrigatórios
        String jsonInvalido = """
                {
                  "nome": "",
                  "material": "",
                  "cor": ""
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

        verify(keycapService, never()).create(any(Keycap.class));
    }

    // Método auxiliar para criar instâncias mockadas de Keycap
    private Keycap criarKeycap(Long id, String nome, String material, String cor) {
        Keycap k = new Keycap();
        k.setId(id);
        k.setNome(nome);
        k.setMaterial(material);
        k.setCor(cor);
        return k;
    }
}