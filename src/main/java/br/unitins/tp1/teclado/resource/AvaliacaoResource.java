package br.unitins.tp1.teclado.resource;

import java.util.Map;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import br.unitins.tp1.teclado.dto.AvaliacaoRequestDTO;
import br.unitins.tp1.teclado.service.AvaliacaoService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@SecurityScheme(securitySchemeName = "KeycloakAuth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8081/realms/ecommerce-teclado/protocol/openid-connect/token")))
@SecurityRequirement(name = "KeycloakAuth")
@Path("/avaliacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AvaliacaoResource {

    @Inject
    AvaliacaoService service;

    @Inject
    JsonWebToken jwt;

    @POST
    @RolesAllowed({"USER", "ADMIN"})
    public Response insert(@Valid AvaliacaoRequestDTO dto) {
        String login = jwt.getName();
        service.insert(login, dto);
        return Response.status(Status.CREATED).entity(Map.of("mensagem", "Avaliação registrada com sucesso!")).build();
    }

    @GET
    @Path("/teclado/{idTeclado}")
    @PermitAll
    public Response findByTeclado(@PathParam("idTeclado") Long idTeclado) {
        return Response.ok(service.findByTeclado(idTeclado)).build();
    }
}
