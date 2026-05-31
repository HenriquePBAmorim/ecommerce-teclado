package br.unitins.tp1.teclado.resource;

import java.util.List;

import br.unitins.tp1.teclado.dto.CupomRequestDTO;
import br.unitins.tp1.teclado.dto.CupomResponseDTO;
import br.unitins.tp1.teclado.service.CupomService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/cupons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CupomResource {

    @Inject
    CupomService service;

    @POST
    @RolesAllowed({"ADMIN"})
    public Response criar(@Valid CupomRequestDTO dto) {
        return Response.status(Status.CREATED).entity(service.criar(dto)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response atualizar(@PathParam("id") Long id, @Valid CupomRequestDTO dto) {
        return Response.ok(service.atualizar(id, dto)).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response desativar(@PathParam("id") Long id) {
        service.desativar(id);
        return Response.ok(java.util.Map.of("mensagem", "Cupom desativado com sucesso!")).build();
    }

    @GET
    @RolesAllowed({"ADMIN"})
    public List<CupomResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GET
    @Path("/validar/{codigo}")
    @RolesAllowed({"USER", "ADMIN"})
    public Response validarCupom(@PathParam("codigo") String codigo) {
        return Response.ok(service.buscarPorCodigo(codigo)).build();
    }
}
