package br.unitins.tp1.teclado.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.teclado.dto.EnderecoRequestDTO;
import br.unitins.tp1.teclado.dto.EnderecoResponseDTO;
import br.unitins.tp1.teclado.mapper.EnderecoMapper;
import br.unitins.tp1.teclado.model.Endereco;
import br.unitins.tp1.teclado.model.Usuario;
import br.unitins.tp1.teclado.repository.UsuarioRepository;
import br.unitins.tp1.teclado.service.EnderecoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService service;

    @Inject
    UsuarioRepository usuarioRepository;

    // A mágica acontece aqui: injetamos o token da requisição atual
    @Inject
    JsonWebToken jwt;

    /**
     * Método auxiliar privado para pegar o usuário logado a partir do token
     */
    private Usuario getUsuarioLogado() {
        // O getName() pega a claim "upn" (User Principal Name) que configuramos no
        // JwtService (o login)
        String login = jwt.getName();
        return usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Usuário logado não encontrado no banco de dados."));
    }

    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/meus-enderecos")
    @jakarta.annotation.security.RolesAllowed({"USER", "ADMIN"})
    public jakarta.ws.rs.core.Response meusEnderecos() {
        String login = jwt.getName();
        List<EnderecoResponseDTO> lista = service.findMeusEnderecosAtivos(login)
                .stream()
                .map(EnderecoMapper::toResponseDTO)
                .toList();
        return jakarta.ws.rs.core.Response.ok(lista).build();
    }

    @GET
    @RolesAllowed({ "ADMIN" })
    public Response buscarTodos() {
        List<EnderecoResponseDTO> lista = service.findAll()
                .stream()
                .map(EnderecoMapper::toResponseDTO)
                .toList();

        return Response.ok(lista).build();
    }

    @POST
    @RolesAllowed({ "USER", "ADMIN" })
    public Response incluir(@Valid EnderecoRequestDTO dto) {
        // Descobre quem é o usuário pelo token
        Usuario usuarioLogado = getUsuarioLogado();

        // Passa o ID do usuário de forma segura para o Service
        Endereco endereco = service.create(usuarioLogado.getId(), dto);

        return Response.status(Status.CREATED).entity(EnderecoMapper.toResponseDTO(endereco)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "USER", "ADMIN" })
    public Response alterar(@PathParam("id") Long id, @Valid EnderecoRequestDTO dto) {
        // Verifica se o endereço pertence ao usuário logado!
        // service.update(id, dto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "USER", "ADMIN" })
    public Response deletar(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}