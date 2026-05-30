package br.unitins.tp1.teclado.application;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        exception.printStackTrace();

        if (exception instanceof jakarta.ws.rs.WebApplicationException) {
            jakarta.ws.rs.core.Response originalResponse = ((jakarta.ws.rs.WebApplicationException) exception).getResponse();
            ErrorResponse error = new ErrorResponse(String.valueOf(originalResponse.getStatus()), exception.getMessage());
            return Response.fromResponse(originalResponse).entity(error).build();
        }

        ErrorResponse error = new ErrorResponse("500", "Erro interno no servidor. Por favor, contate o suporte.");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
}