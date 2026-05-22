package br.unitins.tp1.teclado.resource;

import br.unitins.tp1.teclado.model.FormaPagamento;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/formas-pagamento")
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @GET
    public Response getFormasPagamento() {
        return Response.ok(FormaPagamento.values()).build();
    }
}
