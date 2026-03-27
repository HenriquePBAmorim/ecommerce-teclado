package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Municipio;

public interface MunicipioService {
    List<Municipio> findAll();

    Municipio findById(Long id);

    List<Municipio> findByNome(String nome);

    List<Municipio> findByEstado(Long idEstado);

    Municipio create(Municipio municipio);

    void update(Long id, Municipio municipio);

    void delete(Long id);
}