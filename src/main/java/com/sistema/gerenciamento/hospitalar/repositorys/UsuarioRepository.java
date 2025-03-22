package com.sistema.gerenciamento.hospitalar.repositorys;

import com.sistema.gerenciamento.hospitalar.models.UsuarioModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

// Define a interface do repositório para a entidade UsuarioModel
public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID>, JpaSpecificationExecutor<UsuarioModel> {

    // Método para buscar um usuário pelo nome, carregando as roles (funções/roles do usuário) de forma imediata (fetch)
    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<UsuarioModel> findByNome(String nome);

    // Verifica se já existe um usuário com o nome especificado
    boolean existsByNome(String nome);

    // Verifica se já existe um usuário com o código de usuário especificado
    boolean existsByCodigoUsuario(String codigoUsuario);

    // Verifica se já existe um usuário com a senha especificada
    boolean existsBySenha(String senha);

    // Verifica se já existe um usuário associado a um funcionário com o ID fornecido
    boolean existsByFuncionarioUsuarioFuncionarioId(UUID funcionarioModel);
}