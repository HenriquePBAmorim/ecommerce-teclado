package br.unitins.tp1.teclado.dto;

import br.unitins.tp1.teclado.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String login,
        Double saldoCashback) {
    public static UsuarioResponseDTO toDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getSaldoCashback());
    }
}