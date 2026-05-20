package br.unitins.tp1.teclado.application;

public record ValidationFieldError(
        String field,
        String message) {
}