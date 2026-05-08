package br.unitins.tp1.teclado.resource;

import br.unitins.tp1.teclado.dto.AuthRequestDTO;
import br.unitins.tp1.teclado.dto.AuthResponseDTO;
import br.unitins.tp1.teclado.service.AuthService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(@Valid AuthRequestDTO dto) {
        AuthResponseDTO response = authService.login(dto);
        return Response.ok(response).build();
    }
}