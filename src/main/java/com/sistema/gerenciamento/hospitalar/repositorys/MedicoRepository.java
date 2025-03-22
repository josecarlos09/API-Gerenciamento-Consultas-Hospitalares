package com.sistema.gerenciamento.hospitalar.repositorys;

import com.sistema.gerenciamento.hospitalar.models.MedicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

// Define a interface do repositório para a entidade MedicoModel
public interface MedicoRepository extends JpaRepository<MedicoModel, UUID>, JpaSpecificationExecutor<MedicoModel> {

    // Método para verificar se já existe um médico com o CRM fornecido
    boolean existsByCrm(String crm);

    // Método para verificar se já existe um médico com o CNS fornecido
    boolean existsByCns(String cns);

    // Método para verificar se já existe um médico associado a um funcionário com o ID fornecido
    boolean existsByFuncionarioMedicoFuncionarioId(UUID funcionarioId);

    // Método para buscar um médico baseado no ID do funcionário associado
    @Query(value = """
            SELECT * FROM tb_medico
            WHERE funcionario_id = :funcionarioId
            """, nativeQuery = true)
    Optional<MedicoModel> findByFuncionarioMedicoFuncionarioId(UUID funcionarioId);

    // Método para deletar consultas médicas associadas a um médico, dado o ID do médico
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tb_consulta_medica WHERE medico_id = :medicoId", nativeQuery = true)
    void deleteByMedicoId(@Param("medicoId") UUID medicoId);
}