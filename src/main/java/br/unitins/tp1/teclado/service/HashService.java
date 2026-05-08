package br.unitins.tp1.teclado.service;

import org.mindrot.jbcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashService {

    // Método para gerar o Hash da senha que vai pro banco
    public String bcrypt(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("O valor para hash nao pode ser nulo");
        }
        return BCrypt.hashpw(valor, BCrypt.gensalt());
    }

    // Método que verifica se a senha digitada bate com o Hash do banco
    public boolean verificarBcrypt(String valor, String hash) {
        if (valor == null || hash == null) {
            throw new IllegalArgumentException("Valor e hash nao podem ser nulos");
        }
        return BCrypt.checkpw(valor, hash);
    }

    // Rode este main para gerar a senha do import.sql!
    public static void main(String[] args) {
        HashService hashService = new HashService();
        System.out.println("Hash BCrypt para '123456':");
        System.out.println(hashService.bcrypt("123456"));
    }
}