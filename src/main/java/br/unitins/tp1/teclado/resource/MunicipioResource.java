package br.unitins.tp1.teclado.resource;

import java.util.List;
import br.unitins.tp1.teclado.dto.MunicipioRequestDTO;
import br.unitins.tp1.teclado.dto.MunicipioResponseDTO;
import br.unitins.tp1.teclado.mapper.MunicipioMapper;
import br.unitins.tp1.teclado.model.Municipio;
import br.unitins.tp1.teclado.service.MunicipioService;
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

@Path("/municipios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MunicipioResource {

    @Inject
    MunicipioService service;

    @GET
    public Response buscarTodos() {
        List<MunicipioResponseDTO> lista = service.findAll()
                .stream()
                .map(MunicipioMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/{nome}")
    public Response buscarPeloNome(@PathParam("nome") String nome) {
        List<MunicipioResponseDTO> lista = service.findByNome(nome)
                .stream()
                .map(MunicipioMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/estado/{idEstado}")
    public Response buscarPorEstado(@PathParam("idEstado") Long idEstado) {
        List<MunicipioResponseDTO> lista = service.findByEstado(idEstado)
                .stream()
                .map(MunicipioMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPeloId(@PathParam("id") Long id) {
        Municipio municipio = service.findById(id);
        if (municipio == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(MunicipioMapper.toResponseDTO(municipio)).build();
    }

    @POST
    public Response incluir(@Valid MunicipioRequestDTO dto) {
        Municipio municipio = service.create(MunicipioMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(MunicipioMapper.toResponseDTO(municipio)).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid MunicipioRequestDTO dto) {
        if (service.findById(id) == null)
            return Response.status(Status.NOT_FOUND).build();
        service.update(id, MunicipioMapper.toEntity(dto));
        return Response.ok(java.util.Map.of("mensagem", "Município atualizado com sucesso!")).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        if (service.findById(id) == null)
            return Response.status(Status.NOT_FOUND).build();
        service.delete(id);
        return Response.ok(java.util.Map.of("mensagem", "Município deletado com sucesso!")).build();
    }
}