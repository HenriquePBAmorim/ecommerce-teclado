package br.unitins.tp1.teclado.resource;

import java.util.List;
import br.unitins.tp1.teclado.dto.CategoriaRequestDTO;
import br.unitins.tp1.teclado.dto.CategoriaResponseDTO;
import br.unitins.tp1.teclado.mapper.CategoriaMapper;
import br.unitins.tp1.teclado.model.Categoria;
import br.unitins.tp1.teclado.service.CategoriaService;
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

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService service;

    @GET
    public Response buscarTodos() {
        List<CategoriaResponseDTO> lista = service.findAll().stream().map(CategoriaMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/{nome}")
    public Response buscarPeloNome(@PathParam("nome") String nome) {
        List<CategoriaResponseDTO> lista = service.findByNome(nome).stream().map(CategoriaMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPeloId(@PathParam("id") Long id) {
        Categoria categoria = service.findById(id);
        if (categoria == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(CategoriaMapper.toResponseDTO(categoria)).build();
    }

    @POST
    public Response incluir(@Valid CategoriaRequestDTO dto) {
        Categoria categoria = service.create(CategoriaMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(CategoriaMapper.toResponseDTO(categoria)).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid CategoriaRequestDTO dto) {
        if (service.findById(id) == null)
            return Response.status(Status.NOT_FOUND).build();
        service.update(id, CategoriaMapper.toEntity(dto));
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