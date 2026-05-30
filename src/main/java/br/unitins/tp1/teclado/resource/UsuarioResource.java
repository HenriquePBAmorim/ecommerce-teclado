package br.unitins.tp1.teclado.resource;

import java.util.List;
import br.unitins.tp1.teclado.dto.UsuarioRequestDTO;
import br.unitins.tp1.teclado.dto.UsuarioResponseDTO;
import br.unitins.tp1.teclado.dto.CadastroClienteDTO;
import br.unitins.tp1.teclado.dto.UsuarioUpdateDTO;
import br.unitins.tp1.teclado.dto.UpdateSenhaDTO;
import br.unitins.tp1.teclado.model.Usuario;
import br.unitins.tp1.teclado.service.UsuarioService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService service;

    @jakarta.inject.Inject
    org.eclipse.microprofile.jwt.JsonWebToken jwt;

    @GET
    public Response buscarTodos() {
        List<UsuarioResponseDTO> lista = service.findAll()
                .stream()
                .map(UsuarioResponseDTO::toDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Usuario usuario = service.findById(id);
        return Response.ok(UsuarioResponseDTO.toDTO(usuario)).build();
    }

    @POST
    public Response criar(@Valid UsuarioRequestDTO dto) {
        Usuario usuario = service.create(dto);
        return Response.status(Status.CREATED).entity(UsuarioResponseDTO.toDTO(usuario)).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid UsuarioRequestDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/cadastro")
    @PermitAll
    public Response cadastrarCliente(@Valid CadastroClienteDTO dto) {
        Usuario usuario = service.cadastrarCliente(dto);
        return Response.status(Status.CREATED).entity(UsuarioResponseDTO.toDTO(usuario)).build();
    }

    @jakarta.ws.rs.PUT
    @jakarta.ws.rs.Path("/meu-perfil")
    @jakarta.annotation.security.RolesAllowed({"USER", "ADMIN"})
    public jakarta.ws.rs.core.Response atualizarPerfil(UsuarioUpdateDTO dto) {
        String login = jwt.getName();
        return jakarta.ws.rs.core.Response.ok(service.atualizarPerfil(login, dto)).build();
    }

    @jakarta.ws.rs.PATCH
    @jakarta.ws.rs.Path("/alterar-senha")
    @jakarta.annotation.security.RolesAllowed({"USER", "ADMIN"})
    public jakarta.ws.rs.core.Response alterarSenha(@Valid UpdateSenhaDTO dto) {
        String login = jwt.getName();
        service.alterarSenha(login, dto);
        return jakarta.ws.rs.core.Response.noContent().build();
    }

    @jakarta.ws.rs.POST
    @jakarta.ws.rs.Path("/lista-desejos/{idTeclado}")
    @jakarta.annotation.security.RolesAllowed({"USER", "ADMIN"})
    public jakarta.ws.rs.core.Response adicionarNaListaDesejos(@jakarta.ws.rs.PathParam("idTeclado") Long idTeclado) {
        String login = jwt.getName();
        service.adicionarNaListaDesejos(login, idTeclado);
        return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.CREATED).build();
    }

    @jakarta.ws.rs.DELETE
    @jakarta.ws.rs.Path("/lista-desejos/{idTeclado}")
    @jakarta.annotation.security.RolesAllowed({"USER", "ADMIN"})
    public jakarta.ws.rs.core.Response removerDaListaDesejos(@jakarta.ws.rs.PathParam("idTeclado") Long idTeclado) {
        String login = jwt.getName();
        service.removerDaListaDesejos(login, idTeclado);
        return jakarta.ws.rs.core.Response.noContent().build();
    }

    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/lista-desejos")
    @jakarta.annotation.security.RolesAllowed({"USER", "ADMIN"})
    public jakarta.ws.rs.core.Response buscarListaDesejos() {
        String login = jwt.getName();
        return jakarta.ws.rs.core.Response.ok(service.buscarListaDesejos(login)).build();
    }

    @jakarta.ws.rs.PATCH
    @jakarta.ws.rs.Path("/recuperar-senha/gerar-codigo")
    @jakarta.annotation.security.PermitAll
    public jakarta.ws.rs.core.Response gerarCodigoRecuperacao(@jakarta.validation.Valid br.unitins.tp1.teclado.dto.GerarCodigoDTO dto) {
        service.gerarCodigoRecuperacao(dto);
        return jakarta.ws.rs.core.Response.ok("Código de recuperação gerado com sucesso. Verifique seu e-mail.").build();
    }

    @jakarta.ws.rs.PATCH
    @jakarta.ws.rs.Path("/recuperar-senha/confirmar")
    @jakarta.annotation.security.PermitAll
    public jakarta.ws.rs.core.Response atualizarSenhaRecuperada(@jakarta.validation.Valid br.unitins.tp1.teclado.dto.ConfirmarRecuperacaoDTO dto) {
        service.atualizarSenhaRecuperada(dto);
        return jakarta.ws.rs.core.Response.ok("Sua senha foi alterada com sucesso.").build();
    }
}