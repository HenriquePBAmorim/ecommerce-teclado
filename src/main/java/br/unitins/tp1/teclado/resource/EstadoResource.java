package br.unitins.tp1.teclado.resource;

import java.util.List;
import br.unitins.tp1.teclado.dto.EstadoRequestDTO;
import br.unitins.tp1.teclado.dto.EstadoResponseDTO;
import br.unitins.tp1.teclado.mapper.EstadoMapper;
import br.unitins.tp1.teclado.model.Estado;
import br.unitins.tp1.teclado.service.EstadoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/estados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoService service;

    @GET
    public List<EstadoResponseDTO> buscarTodos() {
        return service.findAll().stream().map(EstadoMapper::toResponseDTO).toList();
    }

    @GET
    @Path("/find/{nome}")
    public List<EstadoResponseDTO> buscarPeloNome(@PathParam("nome") String nome) {
        return service.findByNome(nome).stream().map(EstadoMapper::toResponseDTO).toList();
    }

    @GET
    @Path("/{id}")
    public EstadoResponseDTO buscarPeloId(@PathParam("id") Long id) {
        return EstadoMapper.toResponseDTO(service.findById(id));
    }

    @POST
    public EstadoResponseDTO incluir(EstadoRequestDTO dto) {
        Estado estado = service.create(EstadoMapper.toEntity(dto));
        return EstadoMapper.toResponseDTO(estado);
    }

    @PUT
    @Path("/{id}")
    public void alterar(@PathParam("id") Long id, EstadoRequestDTO dto) {
        service.update(id, EstadoMapper.toEntity(dto));
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.delete(id);
    }
}