package br.unitins.tp1.teclado.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FormaPagamento {
    PIX(1, "Pix"),
    BOLETO(2, "Boleto Bancário"),
    CARTAO_CREDITO(3, "Cartão de Crédito");

    private final Integer id;
    private final String label;

    FormaPagamento(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static FormaPagamento valueOf(Integer id) {
        if (id == null) return null;
        for (FormaPagamento fp : FormaPagamento.values()) {
            if (fp.getId().equals(id)) {
                return fp;
            }
        }
        throw new IllegalArgumentException("Id inválido para Forma de Pagamento");
    }
}
