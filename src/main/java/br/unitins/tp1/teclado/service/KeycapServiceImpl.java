package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Keycap;
import br.unitins.tp1.teclado.repository.KeycapRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class KeycapServiceImpl implements KeycapService {

    @Inject
    KeycapRepository repository;

    @Override
    public List<Keycap> findAll() {
        return repository.listAll();
    }

    @Override
    public Keycap findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Keycap> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    @Override
    @Transactional
    public Keycap create(Keycap keycap) {
        repository.persist(keycap);
        return keycap;
    }

    @Override
    @Transactional
    public void update(Long id, Keycap keycap) {
        Keycap entity = repository.findById(id);
        if (entity == null)
            throw new RuntimeException("Keycap não encontrada.");

        entity.setNome(keycap.getNome());
        entity.setMaterial(keycap.getMaterial());
        entity.setCor(keycap.getCor());
        entity.setPerfil(keycap.getPerfil());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}