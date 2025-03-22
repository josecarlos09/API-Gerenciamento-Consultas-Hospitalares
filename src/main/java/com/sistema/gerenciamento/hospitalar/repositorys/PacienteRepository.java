package com.sistema.gerenciamento.hospitalar.repositorys;

import com.sistema.gerenciamento.hospitalar.models.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

// Define a interface do repositório para a entidade PacienteModel
public interface PacienteRepository extends JpaRepository<PacienteModel, UUID>, JpaSpecificationExecutor<PacienteModel> {

    // Método para verificar se já existe um paciente com o nome completo fornecido
    boolean existsByNomeCompleto(String nomeCompleto);

    // Método para verificar se já existe um paciente com o CNS (Cadastro Nacional de Saúde) fornecido
    boolean existsByCns(String cns);

    // Método para verificar se já existe um paciente com o CPF fornecido
    boolean existsByCpf(String cpf);

    // Método para verificar se já existe um paciente com o RG fornecido
    boolean existsByRg(String rg);

    // Método para verificar se já existe um paciente com o número de telefone celular fornecido
    boolean existsByTelefoneCelular(String telefoneCelular);
}