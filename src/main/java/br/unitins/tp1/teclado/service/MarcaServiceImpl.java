package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Marca;
import br.unitins.tp1.teclado.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService {

    @Inject
    MarcaRepository repository;

    @Override
    public List<Marca> findAll() {
        return repository.listAll();
    }

    @Override
    public Marca findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Marca> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    @Override
    @Transactional
    public Marca create(Marca marca) {
        repository.persist(marca);
        return marca;
    }

    @Override
    @Transactional
    public void update(Long id, Marca marca) {
        Marca entity = repository.findById(id);
        if (entity == null) {
            throw new RuntimeException("Marca não encontrada.");
        }

        entity.setNome(marca.getNome());
        entity.setDescricao(marca.getDescricao());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}