package br.unitins.tp1.teclado.resource;

import java.util.List;

import br.unitins.tp1.teclado.dto.KeycapRequestDTO;
import br.unitins.tp1.teclado.dto.KeycapResponseDTO;
import br.unitins.tp1.teclado.mapper.KeycapMapper;
import br.unitins.tp1.teclado.model.Keycap;
import br.unitins.tp1.teclado.service.KeycapService;
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

@Path("/keycaps")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KeycapResource {

    @Inject
    KeycapService service;

    @GET
    public Response buscarTodos() {
        List<KeycapResponseDTO> lista = service.findAll()
                .stream()
                .map(KeycapMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPeloId(@PathParam("id") Long id) {
        Keycap keycap = service.findById(id);
        if (keycap == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(KeycapMapper.toResponseDTO(keycap)).build();
    }

    @POST
    public Response incluir(@Valid KeycapRequestDTO dto) {
        Keycap keycap = service.create(KeycapMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(KeycapMapper.toResponseDTO(keycap)).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid KeycapRequestDTO dto) {
        if (service.findById(id) == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        service.update(id, KeycapMapper.toEntity(dto));
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        if (service.findById(id) == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        service.delete(id);
        return Response.noContent().build();
    }
}