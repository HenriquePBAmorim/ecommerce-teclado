package br.unitins.tp1.teclado.resource;

import java.util.List;
import br.unitins.tp1.teclado.dto.SwitchRequestDTO;
import br.unitins.tp1.teclado.dto.SwitchResponseDTO;
import br.unitins.tp1.teclado.mapper.SwitchMapper;
import br.unitins.tp1.teclado.model.Switch;
import br.unitins.tp1.teclado.service.SwitchService;
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

@Path("/switches")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SwitchResource {

    @Inject
    SwitchService service;

    @GET
    public Response buscarTodos() {
        List<SwitchResponseDTO> lista = service.findAll().stream().map(SwitchMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/{nome}")
    public Response buscarPeloNome(@PathParam("nome") String nome) {
        List<SwitchResponseDTO> lista = service.findByNome(nome).stream().map(SwitchMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPeloId(@PathParam("id") Long id) {
        Switch sw = service.findById(id);
        if (sw == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(SwitchMapper.toResponseDTO(sw)).build();
    }

    @POST
    public Response incluir(@Valid SwitchRequestDTO dto) {
        Switch sw = service.create(SwitchMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(SwitchMapper.toResponseDTO(sw)).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid SwitchRequestDTO dto) {
        if (service.findById(id) == null)
            return Response.status(Status.NOT_FOUND).build();
        service.update(id, SwitchMapper.toEntity(dto));
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        if (service.findById(id) == null)
            return Response.status(Status.NOT_FOUND).build();
        service.delete(id);
        return Response.noContent().build();
    }
}