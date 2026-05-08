package br.unitins.tp1.teclado.service;

import br.unitins.tp1.teclado.dto.AuthRequestDTO;
import br.unitins.tp1.teclado.dto.AuthResponseDTO;
import br.unitins.tp1.teclado.model.Usuario;
import br.unitins.tp1.teclado.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Override
    public AuthResponseDTO login(AuthRequestDTO dto) {
        // Busca o usuario pelo login
        Usuario usuario = usuarioRepository.findByLogin(dto.login())
                .orElseThrow(() -> new WebApplicationException("Login ou senha invalidos", Status.UNAUTHORIZED));

        // Verifica se a senha fornecida bate com o hash BCrypt do banco
        if (!hashService.verificarBcrypt(dto.senha(), usuario.getSenhaHash())) {
            throw new WebApplicationException("Login ou senha invalidos", Status.UNAUTHORIZED);
        }

        // Gera o token e retorna o DTO
        String token = jwtService.gerarToken(usuario);
        return new AuthResponseDTO(token, "Bearer");
    }
}