package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Estado;
import br.unitins.tp1.teclado.repository.EstadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {

    @Inject
    EstadoRepository repository;

    @Override
    public List<Estado> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Estado findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Estado findBySigla(String sigla) {
        throw new UnsupportedOperationException("Unimplemented method 'findBySigla'");
    }

    @Override
    public List<Estado> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    @Override
    @Transactional
    public Estado create(Estado estado) {
        repository.persist(estado);
        return estado;
    }

    @Override
    @Transactional
    public void update(Long id, Estado estado) {
        Estado e = findById(id);
        e.setNome(estado.getNome());
        e.setSigla(estado.getSigla());
        e.setRegiao(estado.getRegiao());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}