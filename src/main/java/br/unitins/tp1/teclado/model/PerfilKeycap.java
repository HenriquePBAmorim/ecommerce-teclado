package br.unitins.tp1.teclado.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum PerfilKeycap {
    CHERRY(1L, "Cherry"),
    OEM(2L, "OEM"),
    SA(3L, "SA"),
    XDA(4L, "XDA");

    private final Long id;
    private final String label;

    PerfilKeycap(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static PerfilKeycap valueOf(Long id) {
        if (id == null)
            return null;
        for (PerfilKeycap perfil : PerfilKeycap.values()) {
            if (perfil.getId().equals(id))
                return perfil;
        }
        return null;
    }
}