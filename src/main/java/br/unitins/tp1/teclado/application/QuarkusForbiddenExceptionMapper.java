package br.unitins.tp1.teclado.application;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class QuarkusForbiddenExceptionMapper implements ExceptionMapper<io.quarkus.security.ForbiddenException> {

    @Override
    public Response toResponse(io.quarkus.security.ForbiddenException exception) {
        ErrorResponse error = new ErrorResponse("403", "Acesso negado. Você não tem permissão para acessar este recurso.");
        return Response.status(Response.Status.FORBIDDEN).entity(error).build();
    }
}
