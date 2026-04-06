package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Categoria;
import br.unitins.tp1.teclado.repository.CategoriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CategoriaServiceImpl implements CategoriaService {

    @Inject
    CategoriaRepository repository;

    @Override
    public List<Categoria> findAll() {
        return repository.listAll();
    }

    @Override
    public Categoria findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Categoria> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    @Override
    @Transactional
    public Categoria create(Categoria categoria) {
        repository.persist(categoria);
        return categoria;
    }

    @Override
    @Transactional
    public void update(Long id, Categoria categoria) {
        Categoria entity = repository.findById(id);
        if (entity == null)
            throw new RuntimeException("Categoria não encontrada.");
        entity.setNome(categoria.getNome());
        entity.setDescricao(categoria.getDescricao());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}