package br.unitins.tp1.teclado.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum Formato {
    SESSENTA_POR_CENTO(1L, "60%"),
    TKL(2L, "Tenkeyless (TKL)"),
    FULL_SIZE(3L, "Full Size");

    private final Long id;
    private final String label;

    Formato(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Formato valueOf(Long id) {
        if (id == null)
            return null;
        for (Formato formato : Formato.values()) {
            if (formato.getId().equals(id))
                return formato;
        }
        return null;
    }
}