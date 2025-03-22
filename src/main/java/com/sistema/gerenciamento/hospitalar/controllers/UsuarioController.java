package com.sistema.gerenciamento.hospitalar.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.gerenciamento.hospitalar.dtos.UsuarioRecordDto;
import com.sistema.gerenciamento.hospitalar.models.UsuarioModel;
import com.sistema.gerenciamento.hospitalar.services.UsuarioService;
import com.sistema.gerenciamento.hospitalar.specifications.SpecificationsTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    Logger logger = LogManager.getLogger(UsuarioController.class); // Logger para registrar eventos importantes

    final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Retorna todos os usuários cadastrados com suporte a paginação e specifications.
     *
     * @param spec Filtro de busca dinâmico.
     * @param pageable Paginação dos resultados.
     * @return Página de usuários com hiperlinks de navegação.
     */
    @GetMapping
    public ResponseEntity<Page<UsuarioModel>> getAllUsuarios(SpecificationsTemplate.UsuarioSpec spec,
                                                             Pageable pageable) {
        Page<UsuarioModel> usuarioModelPageSpec = usuarioService.findAll(spec, pageable);
        if (!usuarioModelPageSpec.isEmpty()) {
            for (UsuarioModel usuario : usuarioModelPageSpec.toList()) {
                usuario.add(linkTo(methodOn(UsuarioController.class).getOnUsuario(usuario.getUsuarioId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarioModelPageSpec);
    }

    /**
     * Busca um usuário específico pelo ID.
     *
     * @param usuarioId UUID do usuário a ser consultado.
     * @return Objeto do usuário encontrado.
     */
    @GetMapping("/{usuarioId}")
    public ResponseEntity<Object> getOnUsuario(@PathVariable(value = "usuarioId") UUID usuarioId) {
        logger.debug("GET: getOnUsuario, consulta: {}", usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(usuarioId));
    }

    /**
     * Exclui um usuário com base no seu ID.
     *
     * @param usuarioId UUID do usuário a ser excluído.
     * @return Mensagem de sucesso.
     */
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable(value = "usuarioId") UUID usuarioId) {
        usuarioService.deleteUsuarioId(usuarioService.findById(usuarioId).get());
        logger.debug("DELETE: deleteUsuario, usuarioId recebido: {}", usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
    }

    /**
     * Atualiza os dados de um usuário.
     *
     * @param usuarioId ID do usuário a ser atualizado.
     * @param usuarioRecordDto Dados novos do usuário.
     * @return Usuário atualizado.
     */
    @PutMapping("/{usuarioId}/usuario")
    public ResponseEntity<Object> updateUsuario(@PathVariable(value = "usuarioId") UUID usuarioId,
                                                @RequestBody @Validated(UsuarioRecordDto.UsuarioView.UsuarioPut.class)
                                                @JsonView(UsuarioRecordDto.UsuarioView.UsuarioPut.class)
                                                UsuarioRecordDto usuarioRecordDto) {
        if (usuarioRecordDto == null) {
            logger.warn("PUT: updateUsuario falhou - Corpo da requisição ausente para usuarioId: {}", usuarioId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: O corpo da requisição está ausente.");
        }
        logger.debug("PUT: updateUsuario, usuarioId recebido: {}", usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.updateUsuario(usuarioService.findById(usuarioId).get(), usuarioRecordDto));
    }

    /**
     * Atualiza a senha de um usuário.
     *
     * @param usuarioId ID do usuário.
     * @param dadosUsuarioRecordDto Dados contendo a senha antiga e a nova.
     * @return Mensagem de sucesso ou erro.
     */
    @PutMapping("/{usuarioId}/senha")
    public ResponseEntity<Object> updateSenha(@PathVariable(value = "usuarioId") UUID usuarioId,
                                              @RequestBody @Validated(UsuarioRecordDto.UsuarioView.SenhaPut.class)
                                              @JsonView(UsuarioRecordDto.UsuarioView.SenhaPut.class)
                                              UsuarioRecordDto dadosUsuarioRecordDto) {
        Optional<UsuarioModel> optionalModelUsuario = usuarioService.findById(usuarioId);

        if (optionalModelUsuario.isEmpty()) {
            logger.error("PUT: updateSenha, usuário com ID {} não encontrado", usuarioId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }

        if (!optionalModelUsuario.get().getSenha().equals(dadosUsuarioRecordDto.senhaAntiga())) {
            logger.error("PUT: updateSenha, senha antiga não confere para usuarioId: {}", usuarioId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Senha antiga incorreta!");
        }

        usuarioService.updateSenha(optionalModelUsuario.get(), dadosUsuarioRecordDto);
        logger.debug("PUT: updateSenha, senha atualizada com sucesso para usuarioId: {}", usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body("Senha atualizada com sucesso!");
    }

    /**
     * Atualiza o status de um usuário.
     *
     * @param usuarioId ID do usuário.
     * @param usuarioRecordDto Dados de atualização.
     * @return Usuário com status atualizado.
     */
    @PutMapping("/{usuarioId}/status")
    public ResponseEntity<Object> updateStatus(@PathVariable(value = "usuarioId") UUID usuarioId,
                                               @RequestBody @Validated(UsuarioRecordDto.UsuarioView.StatusUsuarioPut.class)
                                               UsuarioRecordDto usuarioRecordDto) {
        logger.debug("PUT: status do usuário atualizado com sucesso para usuarioId: {}", usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.updateStatusUsuario(usuarioService.findById(usuarioId).get(), usuarioRecordDto));
    }
}