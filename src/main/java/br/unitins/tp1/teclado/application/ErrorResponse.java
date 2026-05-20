package br.unitins.tp1.teclado.application;

public record ErrorResponse(
        String code,
        String message) {
}