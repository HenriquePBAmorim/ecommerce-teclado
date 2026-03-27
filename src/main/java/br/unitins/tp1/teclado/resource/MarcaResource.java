package br.unitins.tp1.teclado.resource;

import java.util.List;
import br.unitins.tp1.teclado.dto.MarcaRequestDTO;
import br.unitins.tp1.teclado.dto.MarcaResponseDTO;
import br.unitins.tp1.teclado.mapper.MarcaMapper;
import br.unitins.tp1.teclado.model.Marca;
import br.unitins.tp1.teclado.service.MarcaService;
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

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService service;

    @GET
    public List<MarcaResponseDTO> buscarTodos() {
        return service.findAll().stream().map(MarcaMapper::toResponseDTO).toList();
    }

    @GET
    @Path("/find/{nome}")
    public List<MarcaResponseDTO> buscarPeloNome(@PathParam("nome") String nome) {
        return service.findByNome(nome).stream().map(MarcaMapper::toResponseDTO).toList();
    }

    @GET
    @Path("/{id}")
    public MarcaResponseDTO buscarPeloId(@PathParam("id") Long id) {
        return MarcaMapper.toResponseDTO(service.findById(id));
    }

    @POST
    public MarcaResponseDTO incluir(MarcaRequestDTO dto) {
        Marca marca = service.create(MarcaMapper.toEntity(dto));
        return MarcaMapper.toResponseDTO(marca);
    }

    @PUT
    @Path("/{id}")
    public void alterar(@PathParam("id") Long id, MarcaRequestDTO dto) {
        service.update(id, MarcaMapper.toEntity(dto));
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.delete(id);
    }
}