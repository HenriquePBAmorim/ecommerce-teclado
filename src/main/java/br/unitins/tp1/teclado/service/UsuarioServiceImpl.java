package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.dto.UsuarioRequestDTO;
import br.unitins.tp1.teclado.dto.UsuarioResponseDTO;
import br.unitins.tp1.teclado.dto.UsuarioUpdateDTO;
import br.unitins.tp1.teclado.dto.UpdateSenhaDTO;
import br.unitins.tp1.teclado.dto.CadastroClienteDTO;
import br.unitins.tp1.teclado.model.Perfil;
import br.unitins.tp1.teclado.model.Usuario;
import br.unitins.tp1.teclado.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Inject
    HashService hashService;

    @Override
    public List<Usuario> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Usuario findById(Long id) {
        Usuario usuario = repository.findById(id);
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado no sistema.");
        }
        return usuario;
    }

    @Override
    @Transactional
    public Usuario create(UsuarioRequestDTO dto) {
        // Regra de Negócio: Não permite logins idênticos no sistema
        if (repository.findByLogin(dto.login()).isPresent()) {
            throw new IllegalArgumentException("Este login já está em uso por outro usuário.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(dto.senha());

        usuario.setPerfil(Perfil.USER);

        repository.persist(usuario);
        return usuario;
    }

    @Override
    @Transactional
    public void update(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(id);
        if (usuario == null) {
            throw new NotFoundException("Não é possível alterar. Usuário não encontrado.");
        }

        // Validação: Impede que o usuário mude o login dele para um que já pertence a
        // outra pessoa
        var usuarioExistente = repository.findByLogin(dto.login());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(id)) {
            throw new IllegalArgumentException("Este login já está em uso por outro usuário.");
        }

        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(dto.senha());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Usuario usuario = repository.findById(id);
        if (usuario == null) {
            throw new NotFoundException("Não é possível deletar. Usuário não encontrado.");
        }
        repository.delete(usuario);
    }

    @Override
    @Transactional
    public Usuario cadastrarCliente(CadastroClienteDTO dto) {
        if (repository.findByLogin(dto.login()).isPresent()) {
            throw new IllegalArgumentException("Este login já está em uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(hashService.bcrypt(dto.senha()));
        
        usuario.setPerfil(Perfil.USER);

        repository.persist(usuario);
        return usuario;
    }

    @Override
    @Transactional
    public UsuarioResponseDTO atualizarPerfil(String login, UsuarioUpdateDTO dto) {
        Usuario usuario = repository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Usuário logado não encontrado no sistema."));

        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());

        return UsuarioResponseDTO.toDTO(usuario);
    }

    @Override
    @Transactional
    public void alterarSenha(String login, UpdateSenhaDTO dto) {
        Usuario usuario = repository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Usuário logado não encontrado no sistema."));

        if (!hashService.verificarBcrypt(dto.senhaAntiga(), usuario.getSenhaHash())) {
            throw new IllegalArgumentException("Senha antiga incorreta.");
        }

        usuario.setSenhaHash(hashService.bcrypt(dto.novaSenha()));
    }
}