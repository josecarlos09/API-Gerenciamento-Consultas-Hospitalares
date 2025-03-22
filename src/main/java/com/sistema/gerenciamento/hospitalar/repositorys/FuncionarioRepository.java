package com.sistema.gerenciamento.hospitalar.repositorys;

import com.sistema.gerenciamento.hospitalar.models.FuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Define a interface do repositório para a entidade FuncionarioModel
public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, UUID>, JpaSpecificationExecutor<FuncionarioModel> {

    // Método para verificar se já existe um funcionário com o nome completo fornecido
    boolean existsByNomeCompleto(String nomeCompleto);

    // Método para verificar se já existe um funcionário com o CPF fornecido
    boolean existsByCpf(String cpf);

    // Método para verificar se já existe um funcionário com o RG fornecido
    boolean existsByRg(String rg);
}