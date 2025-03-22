package com.sistema.gerenciamento.hospitalar.services.impl;

import com.sistema.gerenciamento.hospitalar.dtos.MedicoRecordDto;
import com.sistema.gerenciamento.hospitalar.exceptions.NotFoundException;
import com.sistema.gerenciamento.hospitalar.models.FuncionarioModel;
import com.sistema.gerenciamento.hospitalar.models.MedicoModel;
import com.sistema.gerenciamento.hospitalar.repositorys.ConsultaRepository;
import com.sistema.gerenciamento.hospitalar.repositorys.MedicoRepository;
import com.sistema.gerenciamento.hospitalar.services.MedicoService;
import com.sistema.gerenciamento.hospitalar.specifications.SpecificationsTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicoServiceImpl implements MedicoService {

    // Repositórios necessários para manipulação de dados
    final MedicoRepository medicoRepository;
    final ConsultaRepository consultaRepository;

    // Construtor para injeção de dependências dos repositórios
    public MedicoServiceImpl(MedicoRepository medicoRepository, ConsultaRepository consultaRepository) {
        this.medicoRepository = medicoRepository;
        this.consultaRepository = consultaRepository;
    }

    /**
     * Método para verificar se já existe um médico com o CRM informado.
     *
     * @param crm CRM do médico a ser verificado.
     * @return boolean Retorna true se existir, caso contrário, false.
     */
    @Override
    public boolean existByCrm(String crm) {
        return medicoRepository.existsByCrm(crm);
    }

    /**
     * Método para verificar se já existe um médico com o CNS informado.
     *
     * @param cns CNS do médico a ser verificado.
     * @return boolean Retorna true se existir, caso contrário, false.
     */
    @Override
    public boolean existByCns(String cns) {
        return medicoRepository.existsByCns(cns);
    }

    /**
     * Método para salvar um novo médico.
     *
     * @param medicoRecordDto Dados do médico a serem salvos.
     * @param funcionarioModel Funcionário associado ao médico.
     * @return MedicoModel Retorna o médico salvo no banco de dados.
     */
    @Override
    public Object saveMedico(MedicoRecordDto medicoRecordDto, FuncionarioModel funcionarioModel) {
        // Criação de um novo objeto de MedicoModel
        var medicoModel = new MedicoModel();

        // Copia as propriedades do DTO para o modelo de médico
        BeanUtils.copyProperties(medicoRecordDto, medicoModel);

        // Setando as datas de cadastro e atualização
        medicoModel.setDataCadastro(LocalDateTime.now(ZoneId.of("America/Recife")));
        medicoModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        // Associando o médico ao funcionário
        medicoModel.setFuncionarioMedico(funcionarioModel);

        // Salvando o médico no banco de dados e retornando o resultado
        return medicoRepository.save(medicoModel);
    }

    /**
     * Método para verificar se já existe um médico associado a um funcionário com o ID informado.
     *
     * @param funcionarioId ID do funcionário a ser verificado.
     * @return boolean Retorna true se existir, caso contrário, false.
     */
    @Override
    public boolean existsByFuncionarioMedicoFuncionarioId(UUID funcionarioId) {
        return medicoRepository.existsByFuncionarioMedicoFuncionarioId(funcionarioId);
    }

    /**
     * Método para buscar um médico pelo ID.
     *
     * @param medicoId ID do médico a ser buscado.
     * @return Optional<MedicoModel> Médico encontrado, ou lança exceção se não encontrado.
     * @throws NotFoundException Se o médico não for encontrado, lança uma exceção.
     */
    @Override
    public Optional<MedicoModel> findById(UUID medicoId) {
        Optional<MedicoModel> medicoModel = medicoRepository.findById(medicoId);

        // Tratativa de erro caso o médico não seja encontrado
        if (medicoModel.isEmpty()){
            throw new NotFoundException("ERRO, médico não encontrado!");
        }
        return medicoModel;
    }

    /**
     * Método para listar todos os médicos com filtros e paginação.
     *
     * @param spec Especificação de filtros a serem aplicados na consulta.
     * @param pageable Informações sobre a paginação.
     * @return Page<MedicoModel> Uma página de médicos.
     */
    @Override
    public Page<MedicoModel> fidAll(SpecificationsTemplate.MedicoSpec spec, Pageable pageable) {
        // Retorna a página de médicos com filtros e paginação aplicados
        return medicoRepository.findAll(spec, pageable);
    }

    /**
     * Método para atualizar os dados de um médico.
     *
     * @param medicoModel Médico a ser atualizado.
     * @param medicoRecordDto Dados atualizados do médico.
     * @return MedicoModel Médico atualizado no banco de dados.
     */
    @Override
    public MedicoModel updateMedico(MedicoModel medicoModel, MedicoRecordDto medicoRecordDto) {
        // Atualiza a especialidade do médico com os dados do DTO
        medicoModel.setEspecialidade(medicoRecordDto.especialidade());
        medicoModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        // Salva o médico atualizado e retorna o modelo atualizado
        return medicoRepository.save(medicoModel);
    }

    /**
     * Método para excluir um médico pelo ID.
     *
     * @param medicoModel Médico a ser excluído.
     */
    @Transactional
    @Override
    public void deleteByMedicoId(MedicoModel medicoModel) {
        // Exclui o médico da base de dados
        medicoRepository.delete(medicoModel);
    }
}