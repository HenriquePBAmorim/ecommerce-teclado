package br.unitins.tp1.teclado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Cupom extends DefaultEntity {

    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(nullable = false)
    private Double percentualDesconto;

    private Boolean ativo = true;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo != null ? codigo.toUpperCase() : null;
    }

    public Double getPercentualDesconto() {
        return percentualDesconto;
    }

    public void setPercentualDesconto(Double percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
