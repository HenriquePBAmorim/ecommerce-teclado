package br.unitins.tp1.teclado.resource;

import br.unitins.tp1.teclado.service.TecladoService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/vitrine/teclados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VitrineResource {

    @Inject
    TecladoService tecladoService;

    @GET
    @PermitAll
    public Response listarParaVitrine() {
        return Response.ok(tecladoService.buscarParaVitrine()).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Response buscarDetalhes(@PathParam("id") Long id) {
        return Response.ok(tecladoService.buscarDetalhesVitrine(id)).build();
    }
}
