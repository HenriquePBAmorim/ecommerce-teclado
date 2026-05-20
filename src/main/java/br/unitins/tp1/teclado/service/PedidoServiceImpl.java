package br.unitins.tp1.teclado.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.teclado.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.teclado.dto.PedidoRequestDTO;
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

    @Override
    @Transactional
    public Pedido create(Long idUsuario, PedidoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        Endereco endereco = enderecoRepository.findById(dto.idEnderecoEntrega());
        if (endereco == null) {
            throw new RuntimeException("Endereço de entrega não encontrado.");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setDataHora(LocalDateTime.now());

        List<ItemPedido> itensPedido = new ArrayList<>();
        Double valorTotalCalculado = 0.0;

        // Processar cada item enviado no carrinho de compras
        for (ItemPedidoRequestDTO itemDto : dto.itens()) {
            Teclado teclado = tecladoRepository.findById(itemDto.idTeclado());
            if (teclado == null) {
                throw new RuntimeException("Teclado com ID " + itemDto.idTeclado() + " não existe.");
            }

            // Regra Crítica: Validação e baixa do Estoque
            if (teclado.getEstoque() == null || teclado.getEstoque().getQuantidade() < itemDto.quantidade()) {
                throw new RuntimeException("Estoque insuficiente para o modelo: " + teclado.getNome());
            }

            // Subtrai do estoque atual do teclado
            int novoEstoque = teclado.getEstoque().getQuantidade() - itemDto.quantidade();
            teclado.getEstoque().setQuantidade(novoEstoque);

            // Monta a entidade do item
            ItemPedido item = new ItemPedido();
            item.setTeclado(teclado);
            item.setQuantidade(itemDto.quantidade());
            item.setPreco(teclado.getPreco()); // Congela o preço histórico da compra

            itensPedido.add(item);

            // Calcula o subtotal do item e joga no acumulador do pedido
            valorTotalCalculado += item.getPreco() * item.getQuantidade();
        }

        pedido.setItens(itensPedido);
        pedido.setValorTotal(valorTotalCalculado);

        repository.persist(pedido);
        return pedido;
    }

    @Override
    public List<Pedido> findByUsuario(Long idUsuario) {
        return repository.findByUsuario(idUsuario).list();
    }

    @Override
    public Pedido findById(Long id) {
        return repository.findById(id);
    }
}