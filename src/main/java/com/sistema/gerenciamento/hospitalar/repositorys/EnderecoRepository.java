package com.sistema.gerenciamento.hospitalar.repositorys;

import com.sistema.gerenciamento.hospitalar.models.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

// Define a interface do repositório para a entidade EnderecoModel
public interface EnderecoRepository extends JpaRepository<EnderecoModel, UUID>, JpaSpecificationExecutor<EnderecoModel> {

    // Define uma consulta personalizada usando SQL nativo para encontrar um endereço associado a um funcionário
    @Query(value = """
            SELECT * FROM tb_endereco
            WHERE funcionario_id = :funcionarioId
            """, nativeQuery = true)
    // Retorna um Optional contendo o EnderecoModel associado ao funcionário especificado pelo ID
    Optional<EnderecoModel> findByFuncionarioEnderecoFuncionarioId(UUID funcionarioId);
}
