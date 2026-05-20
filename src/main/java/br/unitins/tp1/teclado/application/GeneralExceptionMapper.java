package br.unitins.tp1.teclado.application;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        // Imprime o erro no terminal
        exception.printStackTrace();

        ErrorResponse error = new ErrorResponse("500", "Erro interno no servidor. Por favor, contate o suporte.");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
}