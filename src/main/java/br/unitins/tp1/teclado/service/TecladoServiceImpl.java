package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Teclado;
import br.unitins.tp1.teclado.repository.TecladoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TecladoServiceImpl implements TecladoService {

    @Inject
    TecladoRepository repository;

    @Override
    public List<Teclado> findAll() {
        return repository.listAll();
    }

    @Override
    public Teclado findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Teclado> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    @Override
    @Transactional
    public Teclado create(Teclado teclado) {
        repository.persist(teclado);
        return teclado;
    }

    @Override
    @Transactional
    public void update(Long id, Teclado teclado) {
        Teclado entity = repository.findById(id);
        if (entity == null)
            throw new RuntimeException("Teclado não encontrado.");

        entity.setNome(teclado.getNome());
        entity.setTipoSwitch(teclado.getTipoSwitch());
        entity.setFormato(teclado.getFormato());
        entity.setPreco(teclado.getPreco());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}