package br.unitins.tp1.teclado.service;

import java.util.Set;
import br.unitins.tp1.teclado.model.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtService {

    // Tempo de expiração: 24 horas em segundos
    private static final long EXPIRACAO_SEGUNDOS = 24 * 3600L;

    public String gerarToken(Usuario usuario) {
        return Jwt.issuer("teclado-api")
                .upn(usuario.getLogin())
                .groups(Set.of(usuario.getPerfil().name()))
                .expiresIn(EXPIRACAO_SEGUNDOS)
                .sign();
    }

    public static void main(String[] args) throws Exception {
        java.security.KeyPairGenerator kpg = java.security.KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        java.security.KeyPair kp = kpg.generateKeyPair();

        System.out.println("-----BEGIN PRIVATE KEY-----");
        System.out.println(
                java.util.Base64.getMimeEncoder(64, new byte[] { '\n' }).encodeToString(kp.getPrivate().getEncoded()));
        System.out.println("-----END PRIVATE KEY-----\n");

        System.out.println("-----BEGIN PUBLIC KEY-----");
        System.out.println(
                java.util.Base64.getMimeEncoder(64, new byte[] { '\n' }).encodeToString(kp.getPublic().getEncoded()));
        System.out.println("-----END PUBLIC KEY-----");
    }
}