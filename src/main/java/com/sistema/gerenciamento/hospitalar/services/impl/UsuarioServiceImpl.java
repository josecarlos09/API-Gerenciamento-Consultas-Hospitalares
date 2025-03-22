package com.sistema.gerenciamento.hospitalar.services.impl;

import com.sistema.gerenciamento.hospitalar.dtos.UsuarioRecordDto;
import com.sistema.gerenciamento.hospitalar.enums.RoleType;
import com.sistema.gerenciamento.hospitalar.enums.StatusUsuario;
import com.sistema.gerenciamento.hospitalar.enums.UsuarioType;
import com.sistema.gerenciamento.hospitalar.exceptions.NotFoundException;
import com.sistema.gerenciamento.hospitalar.models.FuncionarioModel;
import com.sistema.gerenciamento.hospitalar.models.UsuarioModel;
import com.sistema.gerenciamento.hospitalar.repositorys.FuncionarioRepository;
import com.sistema.gerenciamento.hospitalar.repositorys.UsuarioRepository;
import com.sistema.gerenciamento.hospitalar.services.RoleService;
import com.sistema.gerenciamento.hospitalar.services.UsuarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    // Logger para registrar mensagens de log
    Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);

    // Repositórios e serviços necessários para a manipulação de dados de usuário
    final UsuarioRepository usuarioRepository;
    final FuncionarioRepository funcionarioRepository;
    final RoleService roleService;
    final PasswordEncoder passwordEncoder;

    // Construtor para injeção de dependência de todos os componentes necessários
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, FuncionarioRepository funcionarioRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método para buscar todos os usuários com paginação e filtro por especificação.
     *
     * @param spec Especificação para filtro dinâmico.
     * @param pageable Paginação para divisão de resultados.
     * @return Page<UsuarioModel> Retorna uma página de usuários.
     */
    @Override
    public Page<UsuarioModel> findAll(Specification<UsuarioModel> spec, Pageable pageable) {
        return usuarioRepository.findAll(spec, pageable);
    }

    /**
     * Método para buscar um usuário pelo ID.
     *
     * @param usuarioId ID do usuário a ser buscado.
     * @return Optional<UsuarioModel> Retorna o usuário encontrado ou um Optional vazio.
     * @throws NotFoundException Caso o usuário não seja encontrado.
     */
    @Override
    public Optional<UsuarioModel> findById(UUID usuarioId) {
        Optional<UsuarioModel> usuarioModelOptional = usuarioRepository.findById(usuarioId);

        if (usuarioModelOptional.isEmpty()) {
            logger.error("ERRO, USUARIO NÃO ENCONTRADO!");  // Registra o erro no log
            throw new NotFoundException("ERRO, USUARIO NÃO ENCONTRADO!");  // Lança uma exceção
        }
        return usuarioModelOptional;
    }

    /**
     * Método para excluir um usuário pelo modelo.
     *
     * @param usuarioModel Modelo do usuário a ser excluído.
     * @return UsuarioModel Retorna o usuário excluído.
     */
    @Override
    public UsuarioModel deleteUsuarioId(UsuarioModel usuarioModel) {
        usuarioRepository.delete(usuarioModel);
        return usuarioModel;
    }

    /**
     * Método para atualizar os dados de um usuário.
     *
     * @param usuarioModel Modelo do usuário a ser atualizado.
     * @param usuarioRecordDto Dados atualizados do usuário.
     * @return UsuarioModel Retorna o usuário com os dados atualizados.
     */
    @Override
    public UsuarioModel updateUsuario(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto) {
        // Atualiza os dados do usuário
        usuarioModel.setNome(usuarioRecordDto.nome());
        usuarioModel.setStatusUsuario(StatusUsuario.ATIVO);  // Define o status como ativo
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));  // Atualiza a data de modificação

        return usuarioRepository.save(usuarioModel);  // Salva o usuário atualizado
    }

    /**
     * Método para atualizar a senha de um usuário.
     *
     * @param usuarioModel Modelo do usuário a ter a senha atualizada.
     * @param usuarioRecordDto Dados com a nova senha.
     * @return UsuarioModel Retorna o usuário com a senha atualizada.
     */
    @Override
    public UsuarioModel updateSenha(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto) {
        usuarioModel.setSenha(usuarioRecordDto.senha());  // Atualiza a senha
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));  // Atualiza a data de modificação

        return usuarioRepository.save(usuarioModel);  // Salva o usuário com a nova senha
    }

    /**
     * Método para atualizar o status de um usuário.
     *
     * @param usuarioModel Modelo do usuário a ter o status atualizado.
     * @param usuarioRecordDto Dados com o novo status.
     * @return UsuarioModel Retorna o usuário com o status atualizado.
     */
    @Override
    public UsuarioModel updateStatusUsuario(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto) {
        usuarioModel.setStatusUsuario(usuarioRecordDto.statusUsuario());  // Atualiza o status do usuário
        return usuarioRepository.save(usuarioModel);  // Salva o usuário com o novo status
    }

    /**
     * Método para verificar se já existe um usuário com o mesmo nome.
     *
     * @param nome Nome do usuário a ser verificado.
     * @return boolean Retorna true se o nome já existir, caso contrário, false.
     */
    @Override
    public boolean existByNome(String nome) {
        return usuarioRepository.existsByNome(nome);
    }

    /**
     * Método para verificar se já existe um usuário com o mesmo código de usuário.
     *
     * @param codigoUsuario Código do usuário a ser verificado.
     * @return boolean Retorna true se o código já existir, caso contrário, false.
     */
    @Override
    public boolean existByCodigoUsuario(String codigoUsuario) {
        return usuarioRepository.existsByCodigoUsuario(codigoUsuario);
    }

    /**
     * Método para verificar se já existe um usuário com a mesma senha.
     *
     * @param senha Senha do usuário a ser verificada.
     * @return boolean Retorna true se a senha já existir, caso contrário, false.
     */
    @Override
    public boolean existBySenha(String senha) {
        return usuarioRepository.existsBySenha(senha);
    }

    /**
     * Método para verificar se já existe um usuário vinculado a um funcionário.
     *
     * @param funcionarioId ID do funcionário a ser verificado.
     * @return boolean Retorna true se existir um usuário vinculado ao funcionário, caso contrário, false.
     */
    @Override
    public boolean existsByFuncionarioUsuarioFuncionarioId(UUID funcionarioId) {
        return usuarioRepository.existsByFuncionarioUsuarioFuncionarioId(funcionarioId);
    }

    /**
     * Método para salvar um novo usuário no sistema.
     *
     * @param usuarioRecordDto Dados do novo usuário.
     * @param funcionarioModel Modelo do funcionário associado ao usuário.
     * @return UsuarioModel Retorna o usuário recém-criado.
     */
    @Transactional
    @Override
    public UsuarioModel saveUsuario(UsuarioRecordDto usuarioRecordDto, FuncionarioModel funcionarioModel) {
        // Instanciando o modelo de usuário
        var usuarioModel = new UsuarioModel();
        // Convertendo os dados do DTO para o modelo
        BeanUtils.copyProperties(usuarioRecordDto, usuarioModel);

        // Setando campos necessários
        usuarioModel.setUsuarioType(UsuarioType.USUARIO);  // Define o tipo de usuário
        usuarioModel.setStatusUsuario(StatusUsuario.ATIVO);  // Define o status do usuário como ativo
        usuarioModel.setDataCriacao(LocalDateTime.now(ZoneId.of("America/Recife")));  // Define a data de criação
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));  // Define a data de atualização

        // Associando o funcionário ao usuário
        usuarioModel.setFuncionarioUsuario(funcionarioModel);

        // Codificando a senha antes de salvar no banco de dados
        usuarioModel.setSenha(passwordEncoder.encode(usuarioModel.getSenha()));

        // Adiciona a role de usuário ao conjunto de roles do usuário
        usuarioModel.getRoles().add(roleService.findByRoleNome(RoleType.ROLE_USUARIO));

        // Salvando o usuário no banco de dados
        return usuarioRepository.save(usuarioModel);
    }
}