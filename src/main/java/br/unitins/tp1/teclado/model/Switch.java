package br.unitins.tp1.teclado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Switch extends DefaultEntity {

    private String nome;
    private String fabricante;

    @Column(name = "id_tipo_switch")
    private TipoSwitch tipo;

    private Double forcaAtuacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public TipoSwitch getTipo() {
        return tipo;
    }

    public void setTipo(TipoSwitch tipo) {
        this.tipo = tipo;
    }

    public Double getForcaAtuacao() {
        return forcaAtuacao;
    }

    public void setForcaAtuacao(Double forcaAtuacao) {
        this.forcaAtuacao = forcaAtuacao;
    }

}