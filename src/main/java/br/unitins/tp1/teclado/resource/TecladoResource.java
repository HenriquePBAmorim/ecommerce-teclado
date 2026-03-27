package br.unitins.tp1.teclado.resource;

import java.util.List;
import br.unitins.tp1.teclado.dto.TecladoRequestDTO;
import br.unitins.tp1.teclado.dto.TecladoResponseDTO;
import br.unitins.tp1.teclado.mapper.TecladoMapper;
import br.unitins.tp1.teclado.model.Teclado;
import br.unitins.tp1.teclado.service.TecladoService;
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

@Path("/teclados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TecladoResource {

    @Inject
    TecladoService service;

    @GET
    public List<TecladoResponseDTO> buscarTodos() {
        return service.findAll().stream().map(TecladoMapper::toResponseDTO).toList();
    }

    @GET
    @Path("/find/{nome}")
    public List<TecladoResponseDTO> buscarPeloNome(@PathParam("nome") String nome) {
        return service.findByNome(nome).stream().map(TecladoMapper::toResponseDTO).toList();
    }

    @GET
    @Path("/{id}")
    public TecladoResponseDTO buscarPeloId(@PathParam("id") Long id) {
        return TecladoMapper.toResponseDTO(service.findById(id));
    }

    @POST
    public TecladoResponseDTO incluir(TecladoRequestDTO dto) {
        Teclado teclado = service.create(TecladoMapper.toEntity(dto));
        return TecladoMapper.toResponseDTO(teclado);
    }

    @PUT
    @Path("/{id}")
    public void alterar(@PathParam("id") Long id, TecladoRequestDTO dto) {
        service.update(id, TecladoMapper.toEntity(dto));
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.delete(id);
    }
}