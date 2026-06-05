package br.unitins.tp1.teclado.application;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<io.quarkus.security.UnauthorizedException> {

    @Override
    public Response toResponse(io.quarkus.security.UnauthorizedException exception) {
        ErrorResponse error = new ErrorResponse("401", "Acesso não autorizado. Faça o login para continuar.");
        return Response.status(Response.Status.UNAUTHORIZED).entity(error).build();
    }
}
