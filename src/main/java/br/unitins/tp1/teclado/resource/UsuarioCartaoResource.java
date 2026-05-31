package br.unitins.tp1.teclado.resource;

import br.unitins.tp1.teclado.dto.CartaoCreditoRequestDTO;
import br.unitins.tp1.teclado.service.UsuarioCartaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/meus-cartoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioCartaoResource {

    @Inject
    UsuarioCartaoService service;

    @Inject
    JsonWebToken jwt;

    @POST
    @RolesAllowed({"USER", "ADMIN"})
    public Response adicionarCartao(@Valid CartaoCreditoRequestDTO dto) {
        String login = jwt.getName();
        return Response.status(Response.Status.CREATED).entity(service.adicionarCartao(login, dto)).build();
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    public Response listarCartoes() {
        String login = jwt.getName();
        return Response.ok(service.listarCartoes(login)).build();
    }

    @PATCH
    @Path("/{idCartao}/desativar")
    @RolesAllowed({"USER", "ADMIN"})
    public Response desativarCartao(@PathParam("idCartao") Long idCartao) {
        String login = jwt.getName();
        service.desativarCartao(login, idCartao);
        return Response.ok(java.util.Map.of("mensagem", "Cartão desativado com sucesso!")).build();
    }
}
