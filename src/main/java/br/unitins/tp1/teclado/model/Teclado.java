package br.unitins.tp1.teclado.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.util.List;

@Entity
public class Teclado extends Produto {

    private String modelo;
    private String idioma;
    private Boolean comFio;
    private Boolean iluminacaoRgb;
    private Formato formato;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_estoque", unique = true)
    private Estoque estoque;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "id_switch")
    private Switch switchTeclado;

    @ManyToOne
    @JoinColumn(name = "id_keycap")
    private Keycap keycap;

    @ManyToMany
    @JoinTable(name = "teclado_categoria", joinColumns = @JoinColumn(name = "id_teclado"), inverseJoinColumns = @JoinColumn(name = "id_categoria"))
    private List<Categoria> categorias;

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Boolean getComFio() {
        return comFio;
    }

    public void setComFio(Boolean comFio) {
        this.comFio = comFio;
    }

    public Boolean getIluminacaoRgb() {
        return iluminacaoRgb;
    }

    public void setIluminacaoRgb(Boolean iluminacaoRgb) {
        this.iluminacaoRgb = iluminacaoRgb;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Switch getSwitchTeclado() {
        return switchTeclado;
    }

    public void setSwitchTeclado(Switch switchTeclado) {
        this.switchTeclado = switchTeclado;
    }

    public Keycap getKeycap() {
        return keycap;
    }

    public void setKeycap(Keycap keycap) {
        this.keycap = keycap;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

}