package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.dto.UsuarioRequestDTO;
import br.unitins.tp1.teclado.model.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();

    Usuario findById(Long id);

    Usuario create(UsuarioRequestDTO dto);

    void update(Long id, UsuarioRequestDTO dto);

    void delete(Long id);
}