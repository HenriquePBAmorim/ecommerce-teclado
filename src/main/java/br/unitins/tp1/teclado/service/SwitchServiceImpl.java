package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Switch;
import br.unitins.tp1.teclado.repository.SwitchRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SwitchServiceImpl implements SwitchService {

    @Inject
    SwitchRepository repository;

    @Override
    public List<Switch> findAll() {
        return repository.listAll();
    }

    @Override
    public Switch findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Switch> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    @Override
    @Transactional
    public Switch create(Switch sw) {
        repository.persist(sw);
        return sw;
    }

    @Override
    @Transactional
    public void update(Long id, Switch sw) {
        Switch entity = repository.findById(id);
        if (entity == null)
            throw new RuntimeException("Switch não encontrado.");

        entity.setNome(sw.getNome());
        entity.setFabricante(sw.getFabricante());
        entity.setTipo(sw.getTipo());
        entity.setForcaAtuacao(sw.getForcaAtuacao());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}