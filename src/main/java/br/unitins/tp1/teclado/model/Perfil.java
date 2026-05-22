package br.unitins.tp1.teclado.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Perfil {
    ADMIN(1L, "Administrador"),
    USER(2L, "Usuário");

    private final Long id;
    private final String label;

    Perfil(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Perfil valueOf(Long id) {
        if (id == null) return null;
        for (Perfil e : Perfil.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Id inválido no Enum: " + id);
    }
}