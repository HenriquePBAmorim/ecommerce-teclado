package br.unitins.tp1.teclado.resource;

import java.util.List;

import br.unitins.tp1.teclado.dto.EstadoRequestDTO;
import br.unitins.tp1.teclado.dto.EstadoResponseDTO;
import br.unitins.tp1.teclado.mapper.EstadoMapper;
import br.unitins.tp1.teclado.model.Estado;
import br.unitins.tp1.teclado.service.EstadoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/estados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoService service;

    @GET
    public Response buscarTodos() {
        List<EstadoResponseDTO> lista = service.findAll()
                .stream()
                .map(EstadoMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/{nome}")
    public Response buscarPeloNome(String nome) {
        List<EstadoResponseDTO> lista = service.findByNome(nome)
                .stream()
                .map(EstadoMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPeloId(Long id) {
        Estado estado = service.findById(id);
        if (estado == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(EstadoMapper.toResponseDTO(estado)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(Long id) {
        if (service.findById(id) == null)
            return Response.status(Status.NOT_FOUND).build();
        service.delete(id);
        return Response.ok(java.util.Map.of("mensagem", "Estado deletado com sucesso!")).build();
    }

    @POST
    public Response incluir(@Valid EstadoRequestDTO dto) {
        Estado estado = service.create(EstadoMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(EstadoMapper.toResponseDTO(estado)).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(Long id, @Valid EstadoRequestDTO dto) {
        if (service.findById(id) == null)
            return Response.status(Status.NOT_FOUND).build();
        service.update(id, EstadoMapper.toEntity(dto));
        return Response.ok(java.util.Map.of("mensagem", "Estado atualizado com sucesso!")).build();
    }
}