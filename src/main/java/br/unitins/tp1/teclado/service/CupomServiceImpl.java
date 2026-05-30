package br.unitins.tp1.teclado.service;

import java.util.List;

import br.unitins.tp1.teclado.dto.CupomRequestDTO;
import br.unitins.tp1.teclado.dto.CupomResponseDTO;
import br.unitins.tp1.teclado.model.Cupom;
import br.unitins.tp1.teclado.repository.CupomRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CupomServiceImpl implements CupomService {

    @Inject
    CupomRepository repository;

    @Override
    @Transactional
    public CupomResponseDTO criar(CupomRequestDTO dto) {
        Cupom cupom = new Cupom();
        cupom.setCodigo(dto.codigo());
        cupom.setPercentualDesconto(dto.percentualDesconto());
        cupom.setAtivo(true);
        repository.persist(cupom);
        return new CupomResponseDTO(cupom);
    }

    @Override
    @Transactional
    public CupomResponseDTO atualizar(Long id, CupomRequestDTO dto) {
        Cupom cupom = repository.findById(id);
        if (cupom == null) {
            throw new NotFoundException("Cupom não encontrado.");
        }
        cupom.setCodigo(dto.codigo());
        cupom.setPercentualDesconto(dto.percentualDesconto());
        return new CupomResponseDTO(cupom);
    }

    @Override
    @Transactional
    public void desativar(Long id) {
        Cupom cupom = repository.findById(id);
        if (cupom == null) {
            throw new NotFoundException("Cupom não encontrado.");
        }
        cupom.setAtivo(false);
    }

    @Override
    public List<CupomResponseDTO> listarTodos() {
        return repository.findAll().stream().map(CupomResponseDTO::new).toList();
    }

    @Override
    public CupomResponseDTO buscarPorCodigo(String codigo) {
        Cupom cupom = repository.findByCodigo(codigo);
        if (cupom == null) {
            throw new NotFoundException("Cupom inválido ou não encontrado.");
        }
        return new CupomResponseDTO(cupom);
    }
}
