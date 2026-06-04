package br.unitins.tp1.teclado.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Avaliacao extends DefaultEntity {

    private Integer nota;
    private String comentario;
    private LocalDateTime dataAvaliacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_teclado")
    private Teclado teclado;

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Teclado getTeclado() {
        return teclado;
    }

    public void setTeclado(Teclado teclado) {
        this.teclado = teclado;
    }
}
