package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.dto.UsuarioRequestDTO;
import br.unitins.tp1.teclado.dto.UsuarioResponseDTO;
import br.unitins.tp1.teclado.dto.UsuarioUpdateDTO;
import br.unitins.tp1.teclado.dto.UpdateSenhaDTO;
import br.unitins.tp1.teclado.dto.CadastroClienteDTO;
import br.unitins.tp1.teclado.model.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();

    Usuario findById(Long id);

    Usuario create(UsuarioRequestDTO dto);

    void update(Long id, UsuarioRequestDTO dto);

    void delete(Long id);

    Usuario cadastrarCliente(CadastroClienteDTO dto);

    UsuarioResponseDTO atualizarPerfil(String login, UsuarioUpdateDTO dto);

    void alterarSenha(String login, UpdateSenhaDTO dto);

    void adicionarNaListaDesejos(String login, Long idTeclado);

    void removerDaListaDesejos(String login, Long idTeclado);

    List<br.unitins.tp1.teclado.dto.TecladoResponseDTO> buscarListaDesejos(String login);
}