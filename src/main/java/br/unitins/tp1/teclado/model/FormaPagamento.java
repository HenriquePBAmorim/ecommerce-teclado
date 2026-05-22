package br.unitins.tp1.teclado.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FormaPagamento {
    PIX(1L, "Pix"),
    BOLETO(2L, "Boleto Bancário"),
    CARTAO_CREDITO(3L, "Cartão de Crédito");

    private final Long id;
    private final String label;

    FormaPagamento(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static FormaPagamento valueOf(Long id) {
        if (id == null) return null;
        for (FormaPagamento e : FormaPagamento.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Id inválido no Enum: " + id);
    }
}
