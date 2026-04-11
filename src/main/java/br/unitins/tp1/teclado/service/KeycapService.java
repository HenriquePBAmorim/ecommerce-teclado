package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Keycap;

public interface KeycapService {
    List<Keycap> findAll();

    Keycap findById(Long id);

    List<Keycap> findByNome(String nome);

    Keycap create(Keycap keycap);

    void update(Long id, Keycap keycap);

    void delete(Long id);
}