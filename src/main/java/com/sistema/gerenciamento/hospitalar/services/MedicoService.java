package com.sistema.gerenciamento.hospitalar.services;

import com.sistema.gerenciamento.hospitalar.dtos.MedicoRecordDto;
import com.sistema.gerenciamento.hospitalar.models.FuncionarioModel;
import com.sistema.gerenciamento.hospitalar.models.MedicoModel;
import com.sistema.gerenciamento.hospitalar.specifications.SpecificationsTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface MedicoService {

    // Método para verificar se já existe um médico com o mesmo CRM (Cadastro de Médico).
    // Retorna um booleano indicando se o CRM já está cadastrado.
    boolean existByCrm(String crm);

    // Método para verificar se já existe um médico com o mesmo CNS (Cartão Nacional de Saúde).
    // Retorna um booleano indicando se o CNS já está cadastrado.
    boolean existByCns(String cns);

    // Método para salvar um novo médico. Recebe um DTO com os dados do médico e o modelo do funcionário associado.
    // Retorna um objeto representando o médico salvo, provavelmente o modelo do médico ou algum outro tipo de objeto.
    Object saveMedico(MedicoRecordDto medicoRecordDto, FuncionarioModel funcionarioModel);

    // Método para verificar se já existe um médico associado a um funcionário com um ID específico.
    // Retorna um booleano indicando se o médico está associado ao funcionário.
    boolean existsByFuncionarioMedicoFuncionarioId(UUID funcionarioId);

    // Método para buscar um médico pelo seu ID único (UUID). Retorna um Optional para evitar NullPointerExceptions
    // caso o médico não seja encontrado.
    Optional<MedicoModel> findById(UUID medicoId);

    // Método para buscar todos os médicos com suporte a filtros dinâmicos e paginação.
    // O parâmetro 'spec' permite construir consultas dinâmicas e 'pageable' define a página e o tamanho para paginar os resultados.
    Page<MedicoModel> fidAll(SpecificationsTemplate.MedicoSpec spec, Pageable pageable);

    // Método para atualizar as informações de um médico. Recebe o modelo do médico que será atualizado e o DTO com as novas informações.
    // Retorna o modelo atualizado do médico.
    MedicoModel updateMedico(MedicoModel medicoModel, MedicoRecordDto medicoRecordDto);

    // Método para deletar um médico específico. Recebe o modelo do médico a ser excluído e remove o registro do banco de dados.
    void deleteByMedicoId(MedicoModel medicoModel);
}
