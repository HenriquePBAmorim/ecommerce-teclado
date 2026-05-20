package br.unitins.tp1.teclado.application;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {
    private String code;
    private String message;
    private List<ValidationFieldError> errors = new ArrayList<>();

    public ValidationError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addFieldError(String field, String message) {
        errors.add(new ValidationFieldError(field, message));
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<ValidationFieldError> getErrors() {
        return errors;
    }
}