package com.sistema.gerenciamento.hospitalar.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.gerenciamento.hospitalar.configs.security.TokenJwt;
import com.sistema.gerenciamento.hospitalar.dtos.JwtRecordDto;
import com.sistema.gerenciamento.hospitalar.dtos.LoginRecordDto;
import com.sistema.gerenciamento.hospitalar.dtos.UsuarioRecordDto;
import com.sistema.gerenciamento.hospitalar.services.FuncionarioService;
import com.sistema.gerenciamento.hospitalar.services.UsuarioService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoUsuarioController {

    // Logger para registrar eventos e auxiliar na depuração
    private static final Logger logger = LogManager.getLogger(AutenticacaoUsuarioController.class);

    // Injeção de dependências
    private final UsuarioService usuarioService;
    private final FuncionarioService funcionarioService;
    private final AuthenticationManager authenticationManager;
    private final TokenJwt jwtProvedor;

    // Construtor para injeção via Spring
    public AutenticacaoUsuarioController(UsuarioService usuarioService, FuncionarioService funcionarioService, AuthenticationManager authenticationManager, TokenJwt jwtProvedor) {
        this.usuarioService = usuarioService;
        this.funcionarioService = funcionarioService;
        this.authenticationManager = authenticationManager;
        this.jwtProvedor = jwtProvedor;
    }

    /**
     * Endpoint para autenticação de usuários.
     * Recebe um DTO com nome e senha e retorna um token JWT caso as credenciais sejam válidas.
     *
     * @param loginRecordDto DTO contendo as credenciais do usuário.
     * @return ResponseEntity com o token JWT em caso de sucesso.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtRecordDto> autenticacao(@RequestBody @Valid LoginRecordDto loginRecordDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRecordDto.nome(), loginRecordDto.senha()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtRecordDto(jwtProvedor.gerarJwt(authentication)));
    }

    /**
     * Endpoint para registro de usuários vinculados a um funcionário.
     *
     * @param usuarioRecordDto DTO com os dados do usuário a ser registrado.
     * @param funcionarioId ID do funcionário ao qual o usuário será associado.
     * @return ResponseEntity informando sucesso ou erro no registro do usuário.
     */
    @PostMapping("/registro/{funcionarioId}/funcionario")
    public ResponseEntity<Object> registroUsuario(
            @RequestBody @Validated(UsuarioRecordDto.UsuarioView.RegistroUsuarioPost.class)
            @JsonView(UsuarioRecordDto.UsuarioView.RegistroUsuarioPost.class) UsuarioRecordDto usuarioRecordDto,
            @PathVariable(value = "funcionarioId") UUID funcionarioId) {

        // Verifica se o nome de usuário já existe no sistema
        if (usuarioService.existByNome(usuarioRecordDto.nome())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, USUARIO JÁ EXISTENTE!");
        }

        // Verifica se o código do usuário já está cadastrado
        if (usuarioService.existByCodigoUsuario(usuarioRecordDto.codigoUsuario())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, ESSE CÓDIGO JÁ ESTÁ CADASTRADO!");
        }

        // Verifica se a senha informada já está em uso
        if (usuarioService.existBySenha(usuarioRecordDto.senha())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, ESSA SENHA JÁ ESTÁ EM USO!");
        }

        // Verifica se o funcionário já está associado a um usuário
        if (usuarioService.existsByFuncionarioUsuarioFuncionarioId(funcionarioId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, ESSE FUNCIONARIO JÁ ESTÁ ASSOCIADO A UM USUÁRIO!");
        }

        // Log de depuração
        logger.debug("POST: Registro de usuário {}", usuarioRecordDto);

        // Criação do usuário e retorno da resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(
                usuarioService.saveUsuario(usuarioRecordDto, funcionarioService.findById(funcionarioId).get()));
    }
}
