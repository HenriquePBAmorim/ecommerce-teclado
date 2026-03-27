package br.unitins.tp1.teclado.resource;

import java.util.List;
import br.unitins.tp1.teclado.dto.MunicipioRequestDTO;
import br.unitins.tp1.teclado.dto.MunicipioResponseDTO;
import br.unitins.tp1.teclado.mapper.MunicipioMapper;
import br.unitins.tp1.teclado.model.Municipio;
import br.unitins.tp1.teclado.service.MunicipioService;
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

@Path("/municipios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MunicipioResource {

    @Inject
    MunicipioService service;

    @GET
    public List<MunicipioResponseDTO> buscarTodos() {
        return service.findAll().stream().map(MunicipioMapper::toResponseDTO).toList();
    }

    @GET
    @Path("/find/{nome}")
    public List<MunicipioResponseDTO> buscarPeloNome(@PathParam("nome") String nome) {
        return service.findByNome(nome).stream().map(MunicipioMapper::toResponseDTO).toList();
    }

    @GET
    @Path("/find/estado/{idEstado}")
    public List<MunicipioResponseDTO> buscarPorEstado(@PathParam("idEstado") Long idEstado) {
        return service.findByEstado(idEstado).stream().map(MunicipioMapper::toResponseDTO).toList();
    }

    @GET
    @Path("/{id}")
    public MunicipioResponseDTO buscarPeloId(@PathParam("id") Long id) {
        return MunicipioMapper.toResponseDTO(service.findById(id));
    }

    @POST
    public MunicipioResponseDTO incluir(MunicipioRequestDTO dto) {
        Municipio municipio = service.create(MunicipioMapper.toEntity(dto));
        return MunicipioMapper.toResponseDTO(municipio);
    }

    @PUT
    @Path("/{id}")
    public void alterar(@PathParam("id") Long id, MunicipioRequestDTO dto) {
        service.update(id, MunicipioMapper.toEntity(dto));
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.delete(id);
    }
}