package br.unitins.tp1.teclado.resource;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import br.unitins.tp1.teclado.model.Keycap;
import br.unitins.tp1.teclado.service.KeycapService;
import br.unitins.tp1.teclado.dto.KeycapRequestDTO;
import br.unitins.tp1.teclado.dto.KeycapResponseDTO;
import br.unitins.tp1.teclado.mapper.KeycapMapper;

@Path("/keycaps")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KeycapServiceRecource {

    @Inject
    KeycapService service;

    @GET
    public List<KeycapResponseDTO> getAll() {
        return service.findAll().stream()
                .map(KeycapMapper::toResponseDTO)
                .toList();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Keycap keycap = service.findById(id);
        if (keycap == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(KeycapMapper.toResponseDTO(keycap)).build();
    }

    @GET
    @Path("/search")
    public List<KeycapResponseDTO> getByNome(@QueryParam("nome") String nome) {
        return service.findByNome(nome).stream()
                .map(KeycapMapper::toResponseDTO)
                .toList();
    }

    @POST
    @Transactional
    public Response create(KeycapRequestDTO dto) {
        Keycap keycap = KeycapMapper.toEntity(dto);
        service.create(keycap);
        return Response.status(Status.CREATED).entity(KeycapMapper.toResponseDTO(keycap)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, KeycapRequestDTO dto) {
        Keycap keycap = KeycapMapper.toEntity(dto);
        service.update(id, keycap);
        return Response.ok(KeycapMapper.toResponseDTO(keycap)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
