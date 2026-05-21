package br.unitins.tp1.teclado.service;

import java.util.List;
import br.unitins.tp1.teclado.dto.EnderecoRequestDTO;
import br.unitins.tp1.teclado.mapper.EnderecoMapper;
import br.unitins.tp1.teclado.model.Endereco;
import br.unitins.tp1.teclado.model.Municipio;
import br.unitins.tp1.teclado.model.Usuario;
import br.unitins.tp1.teclado.repository.EnderecoRepository;
import br.unitins.tp1.teclado.repository.MunicipioRepository;
import br.unitins.tp1.teclado.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Override
    public List<Endereco> findByUsuario(Long idUsuario) {
        return repository.findByUsuario(idUsuario).list();
    }

    @Override
    public List<Endereco> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Endereco findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Endereco create(Long idUsuario, EnderecoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        Municipio municipio = municipioRepository.findById(dto.idMunicipio());
        if (municipio == null) {
            throw new RuntimeException("Município não encontrado.");
        }

        Endereco endereco = EnderecoMapper.toEntity(dto);
        endereco.setUsuario(usuario);
        endereco.setMunicipio(municipio);

        repository.persist(endereco);
        return endereco;
    }

    @Override
    @Transactional
    public void update(Long id, EnderecoRequestDTO dto) {
        Endereco entity = repository.findById(id);
        if (entity == null) {
            throw new RuntimeException("Endereço não encontrado.");
        }

        Municipio municipio = municipioRepository.findById(dto.idMunicipio());
        if (municipio == null) {
            throw new RuntimeException("Município não encontrado.");
        }

        entity.setLogradouro(dto.logradouro());
        entity.setNumero(dto.numero());
        entity.setComplemento(dto.complemento());
        entity.setBairro(dto.bairro());
        entity.setCep(dto.cep());
        entity.setMunicipio(municipio);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Endereco endereco = repository.findById(id);
        if (endereco != null) {
            endereco.setAtivo(false);
        }
    }

    @Override
    public List<Endereco> findMeusEnderecosAtivos(String login) {
        return repository.find("usuario.login = ?1 and ativo = true", login).list();
    }
}