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
        // 1. Validação do Usuário
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        // 2. Validação do Endereço de Entrega
        Endereco endereco = enderecoRepository.findById(dto.idEnderecoEntrega());
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço de entrega não encontrado.");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setDataHora(LocalDateTime.now());

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