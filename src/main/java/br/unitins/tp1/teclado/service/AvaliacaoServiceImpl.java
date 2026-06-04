package br.unitins.tp1.teclado.service;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp1.teclado.dto.AvaliacaoRequestDTO;
import br.unitins.tp1.teclado.dto.AvaliacaoResponseDTO;
import br.unitins.tp1.teclado.mapper.AvaliacaoMapper;
import br.unitins.tp1.teclado.model.Avaliacao;
import br.unitins.tp1.teclado.model.Pedido;
import br.unitins.tp1.teclado.model.StatusPedido;
import br.unitins.tp1.teclado.model.Teclado;
import br.unitins.tp1.teclado.model.Usuario;
import br.unitins.tp1.teclado.repository.AvaliacaoRepository;
import br.unitins.tp1.teclado.repository.PedidoRepository;
import br.unitins.tp1.teclado.repository.TecladoRepository;
import br.unitins.tp1.teclado.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class AvaliacaoServiceImpl implements AvaliacaoService {

    @Inject
    AvaliacaoRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    TecladoRepository tecladoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public AvaliacaoResponseDTO insert(String login, AvaliacaoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        Teclado teclado = tecladoRepository.findById(dto.idTeclado());

        if (teclado == null) {
            throw new NotFoundException("Teclado não encontrado.");
        }

        // Regra de Negócio 2 (Avaliação Única)
        Avaliacao avaliacaoExistente = repository.findByUsuarioETeclado(usuario.getId(), teclado.getId());
        if (avaliacaoExistente != null) {
            throw new BadRequestException("Você já avaliou este produto.");
        }

        // Regra de Negócio 1 (Compra Verificada)
        List<Pedido> pedidosDoUsuario = pedidoRepository.findByUsuario(usuario.getId()).list();
        boolean comprou = pedidosDoUsuario.stream()
                .filter(p -> p.getStatus() == StatusPedido.PAGO || p.getStatus() == StatusPedido.ENTREGUE)
                .flatMap(p -> p.getItens().stream())
                .anyMatch(item -> item.getTeclado().getId().equals(teclado.getId()));

        if (!comprou) {
            throw new ForbiddenException("Acesso negado. Você só pode avaliar produtos que já comprou e que estejam pagos ou entregues.");
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setUsuario(usuario);
        avaliacao.setTeclado(teclado);
        avaliacao.setNota(dto.nota());
        avaliacao.setComentario(dto.comentario());
        avaliacao.setDataAvaliacao(LocalDateTime.now());

        repository.persist(avaliacao);

        return AvaliacaoMapper.toResponseDTO(avaliacao);
    }

    @Override
    public List<AvaliacaoResponseDTO> findByTeclado(Long idTeclado) {
        return repository.findByTeclado(idTeclado)
                .stream()
                .map(AvaliacaoMapper::toResponseDTO)
                .toList();
    }
}
