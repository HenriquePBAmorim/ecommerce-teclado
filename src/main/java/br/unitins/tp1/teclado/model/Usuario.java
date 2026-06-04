package br.unitins.tp1.teclado.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario extends DefaultEntity {

    private String nome;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil;

    // Novos campos adicionados para o E-commerce:
    private String cpf;
    private String email;

    private Integer tentativasFalhas = 0;
    private Boolean bloqueado = false;
    private Double saldoCashback = 0.0;

    private String codigoRecuperacao;
    private java.time.LocalDateTime dataExpiracaoCodigo;
    private Boolean codigoUsado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_usuario")
    private List<Telefone> telefones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos;

    @ManyToMany
    @JoinTable(name = "usuario_lista_desejos", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_teclado"))
    private List<Teclado> listaDesejos;

    // Getters e Setters originais
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<Teclado> getListaDesejos() {
        return listaDesejos;
    }

    public void setListaDesejos(List<Teclado> listaDesejos) {
        this.listaDesejos = listaDesejos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigoRecuperacao() {
        return codigoRecuperacao;
    }

    public void setCodigoRecuperacao(String codigoRecuperacao) {
        this.codigoRecuperacao = codigoRecuperacao;
    }

    public java.time.LocalDateTime getDataExpiracaoCodigo() {
        return dataExpiracaoCodigo;
    }

    public void setDataExpiracaoCodigo(java.time.LocalDateTime dataExpiracaoCodigo) {
        this.dataExpiracaoCodigo = dataExpiracaoCodigo;
    }

    public Boolean getCodigoUsado() {
        return codigoUsado;
    }

    public void setCodigoUsado(Boolean codigoUsado) {
        this.codigoUsado = codigoUsado;
    }

    public Integer getTentativasFalhas() {
        return tentativasFalhas;
    }

    public void setTentativasFalhas(Integer tentativasFalhas) {
        this.tentativasFalhas = tentativasFalhas;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Double getSaldoCashback() {
        return saldoCashback;
    }

    public void setSaldoCashback(Double saldoCashback) {
        this.saldoCashback = saldoCashback;
    }
}