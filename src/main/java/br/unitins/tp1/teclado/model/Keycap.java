package br.unitins.tp1.teclado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Keycap extends DefaultEntity {

    private String nome;
    private String material;
    private String cor;

    @Column(name = "id_perfil")
    private PerfilKeycap perfil;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public PerfilKeycap getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilKeycap perfil) {
        this.perfil = perfil;
    }
}