package br.unitins.tp1.teclado.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.teclado.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.teclado.dto.PedidoRequestDTO;
import br.unitins.tp1.teclado.dto.PedidoResponseDTO;
import br.unitins.tp1.teclado.mapper.PedidoMapper;
import br.unitins.tp1.teclado.model.Endereco;
import br.unitins.tp1.teclado.model.ItemPedido;
import br.unitins.tp1.teclado.model.Pedido;
import br.unitins.tp1.teclado.model.Teclado;
import br.unitins.tp1.teclado.model.Usuario;
import br.unitins.tp1.teclado.repository.EnderecoRepository;
import br.unitins.tp1.teclado.repository.PedidoRepository;
import br.unitins.tp1.teclado.repository.TecladoRepository;
import br.unitins.tp1.teclado.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    TecladoRepository tecladoRepository;

    @Inject
    br.unitins.tp1.teclado.repository.CartaoCreditoRepository cartaoRepository;

    @Inject
    br.unitins.tp1.teclado.repository.CupomRepository cupomRepository;

    @Override
    @Transactional
    public Pedido create(Long idUsuario, PedidoRequestDTO dto) {
        // 1. Validação do Usuário
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        // 2. Validação do Endereço de Entrega
        Endereco endereco = enderecoRepository.findById(dto.idEnderecoEntrega());
        if (endereco == null || endereco.getMunicipio() == null || endereco.getMunicipio().getEstado() == null) {
            throw new jakarta.ws.rs.BadRequestException("Endereço de entrega inválido ou incompleto.");
        }

        String siglaEstado = endereco.getMunicipio().getEstado().getSigla();
        Double valorFrete = (siglaEstado.equalsIgnoreCase("TO") || siglaEstado.equalsIgnoreCase("GO")) ? 0.0 : 45.0;

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setValorFrete(valorFrete);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(br.unitins.tp1.teclado.model.StatusPedido.AGUARDANDO_PAGAMENTO);

        // 3. Validação da Forma de Pagamento
        br.unitins.tp1.teclado.model.FormaPagamento formaPagamento = br.unitins.tp1.teclado.model.FormaPagamento.valueOf(dto.idFormaPagamento());
        pedido.setFormaPagamento(formaPagamento);

        if (formaPagamento == br.unitins.tp1.teclado.model.FormaPagamento.CARTAO_CREDITO) {
            if (dto.idCartao() == null) {
                throw new IllegalArgumentException("Cartão não informado para pagamento via Cartão de Crédito.");
            }
            br.unitins.tp1.teclado.model.CartaoCredito cartao = cartaoRepository.findById(dto.idCartao());
            if (cartao == null) {
                throw new IllegalArgumentException("Cartão não encontrado.");
            }
            if (!cartao.getUsuario().getId().equals(idUsuario)) {
                throw new jakarta.ws.rs.ForbiddenException("Este cartão não pertence a você.");
            }
            if (!cartao.getAtivo()) {
                throw new IllegalArgumentException("Este cartão está inativo.");
            }
            pedido.setCartao(cartao);
        }

        List<ItemPedido> itensPedido = new ArrayList<>();
        Double valorTotalCalculado = 0.0;

        // 3. Processamento dos Itens do Carrinho
        for (ItemPedidoRequestDTO itemDto : dto.itens()) {
            Teclado teclado = tecladoRepository.findById(itemDto.idTeclado());
            if (teclado == null) {
                throw new IllegalArgumentException("Teclado com ID " + itemDto.idTeclado() + " não existe.");
            }

            // Validação e Baixa do Estoque
            if (teclado.getEstoque() == null || teclado.getEstoque().getQuantidade() < itemDto.quantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para o modelo: " + teclado.getNome());
            }

            // Atualiza a quantidade disponível no estoque do teclado
            int novoEstoque = teclado.getEstoque().getQuantidade() - itemDto.quantidade();
            teclado.getEstoque().setQuantidade(novoEstoque);

            // Instancia o item com o preço histórico do momento da compra
            ItemPedido item = new ItemPedido();
            item.setTeclado(teclado);
            item.setQuantidade(itemDto.quantidade());
            item.setPreco(teclado.getPreco());

            itensPedido.add(item);

            // Acumula o valor total
            valorTotalCalculado += item.getPreco() * item.getQuantidade();
        }

        pedido.setItens(itensPedido);

        if (dto.codigoCupom() != null && !dto.codigoCupom().trim().isEmpty()) {
            br.unitins.tp1.teclado.model.Cupom cupom = cupomRepository.findByCodigo(dto.codigoCupom());
            if (cupom == null) {
                throw new IllegalArgumentException("Cupom não encontrado.");
            }
            if (!cupom.getAtivo()) {
                throw new IllegalArgumentException("Este cupom está inativo.");
            }
            Double desconto = valorTotalCalculado * (cupom.getPercentualDesconto() / 100.0);
            pedido.setValorDesconto(desconto);
            pedido.setCupom(cupom);
            valorTotalCalculado -= desconto;
        } else {
            pedido.setValorDesconto(0.0);
        }

        if (Boolean.TRUE.equals(dto.usarCashback()) && usuario.getSaldoCashback() != null && usuario.getSaldoCashback() > 0.0) {
            Double descontoCashback = Math.min(valorTotalCalculado, usuario.getSaldoCashback());
            valorTotalCalculado -= descontoCashback;
            
            // Abate do saldo do usuário o que foi usado
            usuario.setSaldoCashback(usuario.getSaldoCashback() - descontoCashback);
            usuarioRepository.persist(usuario);
            
            // Soma o cashback usado ao valor do desconto (para aparecer no ResponseDTO)
            pedido.setValorDesconto(pedido.getValorDesconto() + descontoCashback);
        }

        valorTotalCalculado += pedido.getValorFrete();
        pedido.setValorTotal(valorTotalCalculado);

        repository.persist(pedido);
        return pedido;
    }

    @Override
    public List<Pedido> findByUsuario(Long idUsuario) {
        return repository.findByUsuario(idUsuario).list();
    }

    @Override
    public List<Pedido> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Pedido findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public List<PedidoResponseDTO> meusPedidos(String login) {
        return repository.find("usuario.login", login)
                .list()
                .stream()
                .map(PedidoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public void cancelarPedido(Long idPedido, String loginUsuario) {
        Pedido pedido = repository.findById(idPedido);
        if (pedido == null) {
            throw new jakarta.ws.rs.NotFoundException("Pedido não encontrado.");
        }
        if (!pedido.getUsuario().getLogin().equals(loginUsuario)) {
            throw new jakarta.ws.rs.ForbiddenException("Este pedido não pertence a você.");
        }
        if (pedido.getStatus() != br.unitins.tp1.teclado.model.StatusPedido.AGUARDANDO_PAGAMENTO && 
            pedido.getStatus() != br.unitins.tp1.teclado.model.StatusPedido.PAGO) {
            throw new IllegalArgumentException("Não é possível cancelar um pedido que já foi enviado ou finalizado.");
        }

        pedido.setStatus(br.unitins.tp1.teclado.model.StatusPedido.CANCELADO);

        for (ItemPedido item : pedido.getItens()) {
            Teclado teclado = item.getTeclado();
            if (teclado != null && teclado.getEstoque() != null) {
                int novoEstoque = teclado.getEstoque().getQuantidade() + item.getQuantidade();
                teclado.getEstoque().setQuantidade(novoEstoque);
                tecladoRepository.persist(teclado);
            }
        }
        repository.persist(pedido);
    }

    @Override
    @Transactional
    public void pagarPedido(Long idPedido, String loginUsuario) {
        Pedido pedido = repository.findById(idPedido);
        if (pedido == null) {
            throw new jakarta.ws.rs.NotFoundException("Pedido não encontrado.");
        }
        if (!pedido.getUsuario().getLogin().equals(loginUsuario)) {
            throw new jakarta.ws.rs.ForbiddenException("Este pedido não pertence a você.");
        }
        if (pedido.getStatus() != br.unitins.tp1.teclado.model.StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new IllegalArgumentException("Este pedido já foi pago ou cancelado.");
        }

        pedido.setStatus(br.unitins.tp1.teclado.model.StatusPedido.PAGO);
        repository.persist(pedido);
    }

    @Override
    @Transactional
    public void marcarComoEntregue(Long idPedido) {
        Pedido pedido = repository.findById(idPedido);
        if (pedido == null) {
            throw new jakarta.ws.rs.NotFoundException("Pedido não encontrado.");
        }
        if (pedido.getStatus() != br.unitins.tp1.teclado.model.StatusPedido.ENVIADO && 
            pedido.getStatus() != br.unitins.tp1.teclado.model.StatusPedido.PAGO) {
            throw new IllegalArgumentException("O pedido não está em um estado válido para ser entregue.");
        }

        pedido.setStatus(br.unitins.tp1.teclado.model.StatusPedido.ENTREGUE);
        
        Usuario usuario = pedido.getUsuario();
        Double cashback = pedido.getValorTotal() * 0.05;
        usuario.setSaldoCashback((usuario.getSaldoCashback() == null ? 0.0 : usuario.getSaldoCashback()) + cashback);
        
        usuarioRepository.persist(usuario);
        repository.persist(pedido);
    }
}