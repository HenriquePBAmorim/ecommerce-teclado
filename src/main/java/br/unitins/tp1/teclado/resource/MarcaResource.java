package br.unitins.tp1.teclado.resource;

import java.util.List;
import br.unitins.tp1.teclado.dto.MarcaRequestDTO;
import br.unitins.tp1.teclado.dto.MarcaResponseDTO;
import br.unitins.tp1.teclado.mapper.MarcaMapper;
import br.unitins.tp1.teclado.model.Marca;
import br.unitins.tp1.teclado.service.MarcaService;
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

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService service;

    @GET
    public Response buscarTodos() {
        List<MarcaResponseDTO> lista = service.findAll().stream().map(MarcaMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/{nome}")
    public Response buscarPeloNome(@PathParam("nome") String nome) {
        List<MarcaResponseDTO> lista = service.findByNome(nome).stream().map(MarcaMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPeloId(@PathParam("id") Long id) {
        Marca marca = service.findById(id);
        if (marca == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(MarcaMapper.toResponseDTO(marca)).build();
    }

    @POST
    public Response incluir(@Valid MarcaRequestDTO dto) {
        Marca marca = service.create(MarcaMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(MarcaMapper.toResponseDTO(marca)).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid MarcaRequestDTO dto) {
        if (service.findById(id) == null)
            return Response.status(Status.NOT_FOUND).build();
        service.update(id, MarcaMapper.toEntity(dto));
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