package br.unitins.tp1.teclado.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import br.unitins.tp1.teclado.dto.PedidoRequestDTO;
import br.unitins.tp1.teclado.dto.PedidoResponseDTO;
import br.unitins.tp1.teclado.mapper.PedidoMapper;
import br.unitins.tp1.teclado.model.Pedido;
import br.unitins.tp1.teclado.model.Usuario;
import br.unitins.tp1.teclado.repository.UsuarioRepository;
import br.unitins.tp1.teclado.service.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@SecurityScheme(securitySchemeName = "KeycloakAuth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8081/realms/ecommerce-teclado/protocol/openid-connect/token")))
@SecurityRequirement(name = "KeycloakAuth")
@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService service;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    JsonWebToken jwt;

    private Usuario getUsuarioLogado() {
        String login = jwt.getName();
        return usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Usuário logado não encontrado."));
    }

    @POST
    @RolesAllowed({ "USER", "ADMIN" })
    public Response fazerPedido(@Valid PedidoRequestDTO dto) {
        Usuario usuarioLogado = getUsuarioLogado();

        Pedido pedido = service.create(usuarioLogado.getId(), dto);

        return Response.status(Status.CREATED).entity(PedidoMapper.toResponseDTO(pedido)).build();
    }

    @GET
    @RolesAllowed({ "ADMIN" })
    public Response buscarTodos() {
        List<PedidoResponseDTO> lista = service.findAll()
                .stream()
                .map(PedidoMapper::toResponseDTO)
                .toList();

        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "USER", "ADMIN" })
    public Response buscarPorId(@PathParam("id") Long id) {

        Pedido pedido = service.findById(id);
        if (pedido == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(PedidoMapper.toResponseDTO(pedido)).build();
    }

    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/meus-pedidos")
    @jakarta.annotation.security.RolesAllowed({"USER", "ADMIN"})
    public jakarta.ws.rs.core.Response meusPedidos() {
        String login = jwt.getName();
        return jakarta.ws.rs.core.Response.ok(service.meusPedidos(login)).build();
    }
}