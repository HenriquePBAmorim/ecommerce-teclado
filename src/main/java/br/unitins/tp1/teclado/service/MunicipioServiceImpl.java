package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Municipio;
import br.unitins.tp1.teclado.repository.MunicipioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MunicipioServiceImpl implements MunicipioService {

    @Inject
    MunicipioRepository municipioRepository;

    @Override
    public List<Municipio> findAll() {
        return municipioRepository.listAll();
    }

    @Override
    public Municipio findById(Long id) {
        return municipioRepository.findById(id);
    }

    @Override
    public List<Municipio> findByNome(String nome) {
        return municipioRepository.findByNome(nome).list();
    }

    @Override
    public List<Municipio> findByEstado(Long idEstado) {
        return municipioRepository.findByEstado(idEstado).list();
    }

    @Override
    @Transactional
    public Municipio create(Municipio municipio) {
        municipioRepository.persist(municipio);
        return municipio;
    }

    @Override
    @Transactional
    public void update(Long id, Municipio municipio) {
        Municipio entity = municipioRepository.findById(id);
        if (entity == null)
            throw new RuntimeException("Município não encontrado");

        entity.setNome(municipio.getNome());
        entity.setEstado(municipio.getEstado());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        municipioRepository.deleteById(id);
    }
}