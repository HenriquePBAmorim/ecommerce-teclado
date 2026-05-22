package br.unitins.tp1.teclado.service;

import br.unitins.tp1.teclado.dto.CartaoCreditoRequestDTO;
import br.unitins.tp1.teclado.dto.CartaoCreditoResponseDTO;
import br.unitins.tp1.teclado.model.CartaoCredito;
import br.unitins.tp1.teclado.model.Usuario;
import br.unitins.tp1.teclado.repository.CartaoCreditoRepository;
import br.unitins.tp1.teclado.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class UsuarioCartaoServiceImpl implements UsuarioCartaoService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    CartaoCreditoRepository cartaoRepository;

    @Override
    @Transactional
    public CartaoCreditoResponseDTO adicionarCartao(String login, CartaoCreditoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        CartaoCredito cartao = new CartaoCredito();
        cartao.setNumeroCartao(dto.numeroCartao());
        cartao.setNomeTitular(dto.nomeTitular());
        cartao.setCpfTitular(dto.cpfTitular());
        cartao.setBandeira(dto.bandeira());
        cartao.setUsuario(usuario);
        cartao.setAtivo(true);

        cartaoRepository.persist(cartao);
        return CartaoCreditoResponseDTO.valueOf(cartao);
    }

    @Override
    public List<CartaoCreditoResponseDTO> listarCartoes(String login) {
        return cartaoRepository.find("usuario.login = ?1 and ativo = true", login)
                .stream()
                .map(CartaoCreditoResponseDTO::valueOf)
                .toList();
    }

    @Override
    @Transactional
    public void desativarCartao(String login, Long idCartao) {
        CartaoCredito cartao = cartaoRepository.findById(idCartao);
        if (cartao == null) {
            throw new NotFoundException("Cartão não encontrado.");
        }
        if (!cartao.getUsuario().getLogin().equals(login)) {
            throw new jakarta.ws.rs.ForbiddenException("Este cartão não pertence a você.");
        }
        cartao.setAtivo(false);
    }
}
