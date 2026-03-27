package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Marca;

public interface MarcaService {
    List<Marca> findAll();

    Marca findById(Long id);

    List<Marca> findByNome(String nome);

    Marca create(Marca marca);

    void update(Long id, Marca marca);

    void delete(Long id);
}