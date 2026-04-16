package br.unitins.tp1.teclado.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Estoque extends DefaultEntity {

    private Integer quantidade;

    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

}