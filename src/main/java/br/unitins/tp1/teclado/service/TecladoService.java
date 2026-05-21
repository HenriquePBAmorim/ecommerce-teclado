package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.model.Teclado;
import br.unitins.tp1.teclado.dto.TecladoVitrineDTO;

public interface TecladoService {
    List<Teclado> findAll();

    Teclado findById(Long id);

    List<Teclado> findByNome(String nome);

    Teclado create(Teclado teclado);

    void update(Long id, Teclado teclado);

    void delete(Long id);

    List<TecladoVitrineDTO> listarVitrine();

    TecladoVitrineDTO buscarVitrinePorId(Long id);
}