package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Categoria;

public interface CategoriaService {
    List<Categoria> findAll();

    Categoria findById(Long id);

    List<Categoria> findByNome(String nome);

    Categoria create(Categoria categoria);

    void update(Long id, Categoria categoria);

    void delete(Long id);
}