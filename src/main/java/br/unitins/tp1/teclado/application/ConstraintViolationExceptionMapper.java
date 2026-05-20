package br.unitins.tp1.teclado.application;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        ValidationError error = new ValidationError("400", "Erro de validação dos dados enviados.");

        exception.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            // A linha abaixo limpa o nome do campo.
            // Ex: Transforma "incluir.dto.logradouro" em apenas "logradouro"
            if (field.contains(".")) {
                field = field.substring(field.lastIndexOf('.') + 1);
            }
            error.addFieldError(field, violation.getMessage());
        });

        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}