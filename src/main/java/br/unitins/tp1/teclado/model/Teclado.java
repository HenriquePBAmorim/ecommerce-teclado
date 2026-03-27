package br.unitins.tp1.teclado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Teclado extends DefaultEntity {

    private String nome;

    @Column(name = "id_tipo_switch")
    private TipoSwitch tipoSwitch;

    @Column(name = "id_formato")
    private Formato formato;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

    private Double preco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoSwitch getTipoSwitch() {
        return tipoSwitch;
    }

    public void setTipoSwitch(TipoSwitch tipoSwitch) {
        this.tipoSwitch = tipoSwitch;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

}
