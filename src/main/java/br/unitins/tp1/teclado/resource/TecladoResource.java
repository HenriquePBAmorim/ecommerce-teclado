package br.unitins.tp1.teclado.resource;

import java.util.List;
import br.unitins.tp1.teclado.dto.TecladoRequestDTO;
import br.unitins.tp1.teclado.dto.TecladoResponseDTO;
import br.unitins.tp1.teclado.mapper.TecladoMapper;
import br.unitins.tp1.teclado.model.Teclado;
import br.unitins.tp1.teclado.service.TecladoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.annotation.security.PermitAll;
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

@Path("/teclados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TecladoResource {

    @Inject
    TecladoService service;

    @GET
    @RolesAllowed({ "ADMIN", "USER" }) // Ambos podem listar
    public Response buscarTodos() {
        List<TecladoResponseDTO> lista = service.findAll()
                .stream()
                .map(TecladoMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/{nome}")
    @RolesAllowed({ "ADMIN", "USER" }) // Ambos podem listar
    public Response buscarPeloNome(@PathParam("nome") String nome) {
        List<TecladoResponseDTO> lista = service.findByNome(nome)
                .stream()
                .map(TecladoMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "USER" }) // Ambos podem ver
    public Response buscarPeloId(@PathParam("id") Long id) {
        Teclado teclado = service.findById(id);
        if (teclado == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(TecladoMapper.toResponseDTO(teclado)).build();
    }

    @POST
    @RolesAllowed({ "ADMIN" }) // APENAS ADMIN PODE CADASTRAR
    public Response incluir(@Valid TecladoRequestDTO dto) {
        Teclado teclado = service.create(TecladoMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(TecladoMapper.toResponseDTO(teclado)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "ADMIN" }) // APENAS ADMIN PODE ALTERAR
    public Response alterar(@PathParam("id") Long id, @Valid TecladoRequestDTO dto) {
        if (service.findById(id) == null)
            return Response.status(Status.NOT_FOUND).build();
        service.update(id, TecladoMapper.toEntity(dto));
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "ADMIN" }) // APENAS ADMIN PODE DELETAR
    public Response deletar(@PathParam("id") Long id) {
        if (service.findById(id) == null)
            return Response.status(Status.NOT_FOUND).build();
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/vitrine")
    @PermitAll
    public Response vitrine() {
        return Response.ok(service.listarVitrine()).build();
    }
}