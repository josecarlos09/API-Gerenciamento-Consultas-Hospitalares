package com.sistema.gerenciamento.hospitalar.services.impl;

import com.sistema.gerenciamento.hospitalar.dtos.PacienteRecordDto;
import com.sistema.gerenciamento.hospitalar.enums.StatusPaciente;
import com.sistema.gerenciamento.hospitalar.exceptions.NotFoundException;
import com.sistema.gerenciamento.hospitalar.models.PacienteModel;
import com.sistema.gerenciamento.hospitalar.repositorys.PacienteRepository;
import com.sistema.gerenciamento.hospitalar.services.PacienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class PacienteServiceImpl implements PacienteService {

    // Repositório necessário para manipulação de dados dos pacientes
    final PacienteRepository pacienteRepository;

    // Construtor para injeção de dependência do repositório
    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Método para salvar um novo paciente no banco de dados.
     *
     * @param pacienteRecordDto Dados do paciente a serem salvos.
     * @return PacienteModel Retorna o paciente salvo no banco de dados.
     */
    @Override
    public PacienteModel savePaciente(PacienteRecordDto pacienteRecordDto) {
        // Criação de um novo objeto de PacienteModel
        var pacienteModel = new PacienteModel();

        // Copia as propriedades do DTO para o modelo de paciente
        BeanUtils.copyProperties(pacienteRecordDto, pacienteModel);

        // Setando as datas de cadastro e atualização
        pacienteModel.setDataCadastro(LocalDateTime.now(ZoneId.of("America/Recife")));
        pacienteModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        // Setando o status do paciente como ativo
        pacienteModel.setStatusPaciente(StatusPaciente.ATIVO);

        // Salvando o paciente no banco de dados e retornando o resultado
        return pacienteRepository.save(pacienteModel);
    }

    /**
     * Método para verificar se já existe um paciente com o nome completo informado.
     *
     * @param nomeCompleto Nome completo do paciente a ser verificado.
     * @return boolean Retorna true se o paciente existir, caso contrário, false.
     */
    @Override
    public boolean existByNomeCompleto(String nomeCompleto) {
        return pacienteRepository.existsByNomeCompleto(nomeCompleto);
    }

    /**
     * Método para listar todos os pacientes com filtros e paginação.
     *
     * @param spec Especificação de filtros a serem aplicados na consulta.
     * @param pageable Informações sobre a paginação.
     * @return Page<PacienteModel> Uma página de pacientes.
     */
    @Override
    public Page<PacienteModel> fidAll(Specification<PacienteModel> spec, Pageable pageable) {
        // Retorna a página de pacientes com filtros e paginação aplicados
        return pacienteRepository.findAll(spec, pageable);
    }

    /**
     * Método para buscar um paciente pelo ID.
     *
     * @param pacienteId ID do paciente a ser buscado.
     * @return Optional<PacienteModel> Paciente encontrado ou lança exceção caso não encontrado.
     * @throws NotFoundException Se o paciente não for encontrado, lança uma exceção.
     */
    @Override
    public Optional<PacienteModel> findById(UUID pacienteId) {
        Optional<PacienteModel> pacienteModelOptional = pacienteRepository.findById(pacienteId);

        // Tratativa de erro caso o paciente não seja encontrado
        if (pacienteModelOptional.isEmpty()) {
            throw new NotFoundException("ERRO, paciente não encontrado!");
        }

        return pacienteModelOptional;
    }

    /**
     * Método para atualizar os dados de um paciente.
     *
     * @param pacienteModel Modelo de paciente a ser atualizado.
     * @param pacienteRecordDto Dados atualizados do paciente.
     * @return PacienteModel Paciente atualizado no banco de dados.
     */
    @Override
    public PacienteModel updatePaciente(PacienteModel pacienteModel, PacienteRecordDto pacienteRecordDto) {
        // Atualiza a data de atualização do paciente
        pacienteModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        // Mantém o status do paciente como ATIVO
        pacienteModel.setStatusPaciente(StatusPaciente.ATIVO);

        // Atualiza os campos do paciente com os dados do DTO
        pacienteModel.setTelefoneCelular(pacienteRecordDto.telefoneCelular());

        // Salvando o paciente atualizado e retornando o modelo atualizado
        return pacienteRepository.save(pacienteModel);
    }

    /**
     * Método para atualizar o status de um paciente.
     *
     * @param pacienteModel Modelo de paciente a ter seu status atualizado.
     * @param pacienteRecordDto Dados contendo o novo status do paciente.
     * @return PacienteModel Paciente com o status atualizado.
     */
    @Override
    public PacienteModel updateStatusPaciente(PacienteModel pacienteModel, PacienteRecordDto pacienteRecordDto) {
        // Atualiza o status do paciente
        pacienteModel.setStatusPaciente(pacienteRecordDto.statusPaciente());

        // Atualiza a data de atualização
        pacienteModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        // Salvando o paciente com status atualizado e retornando o modelo
        return pacienteRepository.save(pacienteModel);
    }

    /**
     * Método para verificar se já existe um paciente com o CNS informado.
     *
     * @param cns CNS do paciente a ser verificado.
     * @return boolean Retorna true se o paciente existir, caso contrário, false.
     */
    @Override
    public boolean existByCns(String cns) {
        return pacienteRepository.existsByCns(cns);
    }

    /**
     * Método para verificar se já existe um paciente com o CPF informado.
     *
     * @param cpf CPF do paciente a ser verificado.
     * @return boolean Retorna true se o paciente existir, caso contrário, false.
     */
    @Override
    public boolean existByCpf(String cpf) {
        return pacienteRepository.existsByCpf(cpf);
    }

    /**
     * Método para verificar se já existe um paciente com o RG informado.
     *
     * @param rg RG do paciente a ser verificado.
     * @return boolean Retorna true se o paciente existir, caso contrário, false.
     */
    @Override
    public boolean existByRg(String rg) {
        return pacienteRepository.existsByRg(rg);
    }

    /**
     * Método para verificar se já existe um paciente com o número de telefone celular informado.
     *
     * @param telefoneCelular Número de telefone celular do paciente a ser verificado.
     * @return boolean Retorna true se o paciente existir, caso contrário, false.
     */
    @Override
    public boolean existByTelefoneCelular(String telefoneCelular) {
        return pacienteRepository.existsByTelefoneCelular(telefoneCelular);
    }

    /**
     * Método para excluir um paciente pelo ID.
     *
     * @param pacienteModel Modelo do paciente a ser excluído.
     */
    @Transactional
    @Override
    public void deleteByPacienteId(PacienteModel pacienteModel) {
        // Exclui o paciente da base de dados
        pacienteRepository.delete(pacienteModel);
    }
}