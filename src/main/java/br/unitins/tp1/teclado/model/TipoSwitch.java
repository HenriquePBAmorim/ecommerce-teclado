package br.unitins.tp1.teclado.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum TipoSwitch {
    BLUE(1L, "Blue (Clicky)"),
    RED(2L, "Red (Linear)"),
    BROWN(3L, "Brown (Tactile)");

    private final Long id;
    private final String label;

    TipoSwitch(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TipoSwitch valueOf(Long id) {
        if (id == null)
            return null;
        for (TipoSwitch ts : TipoSwitch.values()) {
            if (ts.getId().equals(id))
                return ts;
        }
        return null;
    }
}