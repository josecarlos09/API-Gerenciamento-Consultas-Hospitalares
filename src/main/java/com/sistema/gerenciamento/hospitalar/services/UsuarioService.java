package com.sistema.gerenciamento.hospitalar.services;

import com.sistema.gerenciamento.hospitalar.dtos.UsuarioRecordDto;
import com.sistema.gerenciamento.hospitalar.models.FuncionarioModel;
import com.sistema.gerenciamento.hospitalar.models.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {

    // Método para buscar todos os usuários com suporte a filtros dinâmicos e paginação.
    // O parâmetro 'spec' permite construir consultas dinâmicas para buscar usuários de forma flexível.
    // O 'pageable' permite paginar os resultados, o que é útil quando o número de usuários é grande.
    Page<UsuarioModel> findAll(Specification<UsuarioModel> spec, Pageable pageable);

    // Método para buscar um usuário pelo seu ID único (UUID). Retorna um Optional para evitar NullPointerExceptions
    // caso o usuário não seja encontrado.
    Optional<UsuarioModel> findById(UUID usuarioId);

    // Método para deletar um usuário específico. Recebe o modelo do usuário a ser excluído e remove o registro do banco de dados.
    // Retorna o modelo de usuário deletado (pode ser útil para confirmação).
    UsuarioModel deleteUsuarioId(UsuarioModel usuarioModel);

    // Método para atualizar as informações de um usuário. Recebe o modelo do usuário e um DTO com os novos dados.
    // Retorna o modelo atualizado do usuário.
    UsuarioModel updateUsuario(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto);

    // Método para atualizar a senha de um usuário. Recebe o modelo do usuário e o DTO com os novos dados da senha.
    // Retorna o modelo de usuário com a senha atualizada.
    UsuarioModel updateSenha(UsuarioModel usuarioModel, UsuarioRecordDto dadosUsuarioRecordDto);

    // Método para atualizar o status de um usuário (ativo, inativo, etc.).
    // Recebe o modelo do usuário e um DTO com os novos dados do status.
    UsuarioModel updateStatusUsuario(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto);

    // Método para verificar se já existe um usuário com o mesmo nome.
    // Retorna um booleano indicando se o nome já está cadastrado.
    boolean existByNome(String nome);

    // Método para verificar se já existe um usuário com o mesmo código de usuário.
    // Retorna um booleano indicando se o código do usuário já está cadastrado.
    boolean existByCodigoUsuario(String codigoUsuario);

    // Método para salvar um novo usuário. Recebe o DTO com os dados do usuário e o modelo do funcionário associado.
    // Retorna o modelo do usuário salvo.
    UsuarioModel saveUsuario(UsuarioRecordDto usuarioRecordDto, FuncionarioModel funcionarioModel);

    // Método para verificar se já existe um usuário com a mesma senha (isso pode ser usado, por exemplo, para verificar se a senha já foi usada anteriormente).
    // Retorna um booleano indicando se a senha já está cadastrada.
    boolean existBySenha(String senha);

    // Método para verificar se já existe um usuário associado a um funcionário com um ID específico.
    // Retorna um booleano indicando se o funcionário está associado a um usuário.
    boolean existsByFuncionarioUsuarioFuncionarioId(UUID funcionarioModel);
}