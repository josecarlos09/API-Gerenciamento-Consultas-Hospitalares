package com.sistema.gerenciamento.hospitalar.services;

import com.sistema.gerenciamento.hospitalar.dtos.FuncionarioRecordDto;
import com.sistema.gerenciamento.hospitalar.models.FuncionarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;
public interface FuncionarioService {

    // Método para buscar um funcionário pelo seu ID único (UUID). Retorna um Optional para evitar NullPointerExceptions
    // caso o funcionário não seja encontrado.
    Optional<FuncionarioModel> findById(UUID funcionarioId);

    // Método para verificar se já existe um funcionário com o mesmo nome completo.
    // Retorna um booleano indicando se o nome completo já está cadastrado.
    boolean existByNomeCompleto(String nomeCompleto);

    // Método para verificar se já existe um funcionário com o mesmo CPF.
    // Retorna um booleano indicando se o CPF já está cadastrado.
    boolean existByCpf(String cpf);

    // Método para salvar um novo funcionário. Recebe um DTO com os dados do funcionário e retorna o modelo do funcionário
    // que foi salvo no banco de dados.
    FuncionarioModel saveFuncionario(FuncionarioRecordDto funcionarioRecordDto);

    // Método para verificar se já existe um funcionário com o mesmo RG.
    // Retorna um booleano indicando se o RG já está cadastrado.
    boolean existByRg(String rg);

    // Método para buscar todos os funcionários com suporte a filtros dinâmicos e paginação.
    // O parâmetro 'spec' permite construir consultas dinâmicas, e 'pageable' define a página e o tamanho para paginar os resultados.
    Page<FuncionarioModel> fidAll(Specification<FuncionarioModel> spec, Pageable pageable);

    // Método para deletar um funcionário específico. Recebe o modelo do funcionário a ser excluído e remove o registro do banco de dados.
    void deleteFuncionario(FuncionarioModel funcionarioModel);

    // Método para atualizar as informações de um funcionário. Recebe o DTO com as novas informações e o modelo do funcionário a ser atualizado.
    FuncionarioModel updateFuncionario(FuncionarioRecordDto funcionarioRecordDto, FuncionarioModel funcionarioModel);

    // Método para atualizar o status de um funcionário. Esse método pode ser utilizado para mudar o status do funcionário (ativo, inativo, etc.).
    FuncionarioModel updateStatusFuncionario(FuncionarioModel funcionarioModel, FuncionarioRecordDto funcionarioRecordDto);
}