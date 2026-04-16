package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Estoque;
import br.unitins.tp1.teclado.model.Teclado;
import br.unitins.tp1.teclado.repository.EstoqueRepository;
import br.unitins.tp1.teclado.repository.TecladoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TecladoServiceImpl implements TecladoService {

    @Inject
    TecladoRepository repository;

    @Inject
    EstoqueRepository estoqueRepository;

    @Override
    public List<Teclado> findAll() {
        return repository.findAll().list();
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
        Estoque estoque = teclado.getEstoque();
        if (estoque != null && estoque.getId() == null) {
            estoqueRepository.persist(estoque);
        }

        repository.persist(teclado);
        return teclado;
    }

    @Override
    @Transactional
    public void update(Long id, Teclado teclado) {
        Teclado entity = repository.findById(id);
        if (entity == null) {
            throw new RuntimeException("Teclado não encontrado.");
        }

        entity.setNome(teclado.getNome());
        entity.setPreco(teclado.getPreco());

        entity.setModelo(teclado.getModelo());
        entity.setIdioma(teclado.getIdioma());
        entity.setComFio(teclado.getComFio());
        entity.setIluminacaoRgb(teclado.getIluminacaoRgb());

        entity.setMarca(teclado.getMarca());
        entity.setSwitchTeclado(teclado.getSwitchTeclado());
        entity.setKeycap(teclado.getKeycap());
        entity.setCategorias(teclado.getCategorias());

        Estoque estoqueExistente = entity.getEstoque();
        if (estoqueExistente == null) {
            estoqueExistente = new Estoque();
            entity.setEstoque(estoqueExistente);
        }

        if (teclado.getEstoque() != null) {
            estoqueExistente.setQuantidade(teclado.getEstoque().getQuantidade());
            estoqueExistente.setDataAtualizacao(teclado.getEstoque().getDataAtualizacao());

            if (estoqueExistente.getId() == null) {
                estoqueRepository.persist(estoqueExistente);
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}