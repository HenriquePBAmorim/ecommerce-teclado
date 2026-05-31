package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Teclado;
import br.unitins.tp1.teclado.repository.TecladoRepository;
import br.unitins.tp1.teclado.dto.TecladoVitrineDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TecladoServiceImpl implements TecladoService {

    @Inject
    TecladoRepository repository;

    @Override
    public List<Teclado> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Teclado findById(Long id) {
        Teclado teclado = repository.findById(id);
        if (teclado == null) {
            throw new NotFoundException("Teclado não encontrado no banco de dados.");
        }
        return teclado;
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
        if (entity == null) {
            throw new NotFoundException("Não é possível alterar. Teclado não encontrado.");
        }

        entity.setNome(teclado.getNome());
        entity.setPreco(teclado.getPreco());
        entity.setModelo(teclado.getModelo());
        entity.setIdioma(teclado.getIdioma());
        entity.setComFio(teclado.getComFio());
        entity.setIluminacaoRgb(teclado.getIluminacaoRgb());
        entity.setEstoque(teclado.getEstoque());
        entity.setMarca(teclado.getMarca());
        entity.setSwitchTeclado(teclado.getSwitchTeclado());
        entity.setKeycap(teclado.getKeycap());
        entity.setCategorias(teclado.getCategorias());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Teclado teclado = repository.findById(id);
        if (teclado == null) {
            throw new NotFoundException("Não é possível deletar. Teclado não encontrado.");
        }
        repository.delete(teclado);
    }

    @Override
    public List<TecladoVitrineDTO> buscarParaVitrine() {
        return repository.find("estoque.quantidade > 0")
                .stream()
                .map(TecladoVitrineDTO::toDTO)
                .toList();
    }

    @Override
    public TecladoVitrineDTO buscarDetalhesVitrine(Long id) {
        Teclado teclado = repository.findById(id);
        if (teclado == null || teclado.getEstoque() == null || teclado.getEstoque().getQuantidade() <= 0) {
            throw new NotFoundException("Produto indisponível ou não encontrado na vitrine.");
        }
        return TecladoVitrineDTO.toDTO(teclado);
    }
}
