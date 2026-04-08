package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Switch;

public interface SwitchService {
    List<Switch> findAll();

    Switch findById(Long id);

    List<Switch> findByNome(String nome);

    Switch create(Switch sw);

    void update(Long id, Switch sw);

    void delete(Long id);
}