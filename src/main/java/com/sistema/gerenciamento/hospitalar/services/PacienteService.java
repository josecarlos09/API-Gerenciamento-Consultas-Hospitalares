package com.sistema.gerenciamento.hospitalar.services;

import com.sistema.gerenciamento.hospitalar.dtos.PacienteRecordDto;
import com.sistema.gerenciamento.hospitalar.models.PacienteModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface PacienteService {

    // Método para salvar um novo paciente. Recebe um DTO com os dados do paciente e retorna o modelo do paciente
    // que foi salvo no banco de dados.
    PacienteModel savePaciente(PacienteRecordDto pacienteRecordDto);

    // Método para verificar se já existe um paciente com o mesmo nome completo.
    // Retorna um booleano indicando se o nome completo já está cadastrado.
    boolean existByNomeCompleto(String nomeCompleto);

    // Método para buscar todos os pacientes com suporte a filtros dinâmicos e paginação.
    // O parâmetro 'spec' permite construir consultas dinâmicas, e 'pageable' define a página e o tamanho para paginar os resultados.
    Page<PacienteModel> fidAll(Specification<PacienteModel> spec, Pageable pageable);

    // Método para buscar um paciente pelo seu ID único (UUID). Retorna um Optional para evitar NullPointerExceptions
    // caso o paciente não seja encontrado.
    Optional<PacienteModel> findById(UUID pacienteId);

    // Método para atualizar as informações de um paciente. Recebe o modelo do paciente a ser atualizado e o DTO com as novas informações.
    // Retorna o modelo atualizado do paciente.
    PacienteModel updatePaciente(PacienteModel pacienteModel, PacienteRecordDto pacienteRecordDto);

    // Método para atualizar o status de um paciente. Esse método pode ser utilizado para mudar o status do paciente (ativo, inativo, etc.).
    PacienteModel updateStatusPaciente(PacienteModel pacienteModel, PacienteRecordDto pacienteRecordDto);

    // Método para verificar se já existe um paciente com o mesmo CNS (Cartão Nacional de Saúde).
    // Retorna um booleano indicando se o CNS já está cadastrado.
    boolean existByCns(String cns);

    // Método para verificar se já existe um paciente com o mesmo CPF.
    // Retorna um booleano indicando se o CPF já está cadastrado.
    boolean existByCpf(String cpf);

    // Método para verificar se já existe um paciente com o mesmo RG.
    // Retorna um booleano indicando se o RG já está cadastrado.
    boolean existByRg(String rg);

    // Método para verificar se já existe um paciente com o mesmo número de telefone celular.
    // Retorna um booleano indicando se o telefone celular já está cadastrado.
    boolean existByTelefoneCelular(String telefoneCelular);

    // Método para deletar um paciente específico. Recebe o modelo do paciente a ser excluído e remove o registro do banco de dados.
    void deleteByPacienteId(PacienteModel pacienteModel);
}