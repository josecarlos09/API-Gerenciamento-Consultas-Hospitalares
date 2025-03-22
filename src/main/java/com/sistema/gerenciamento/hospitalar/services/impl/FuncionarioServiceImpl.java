package com.sistema.gerenciamento.hospitalar.services.impl;

import com.sistema.gerenciamento.hospitalar.dtos.FuncionarioRecordDto;
import com.sistema.gerenciamento.hospitalar.enums.StatusFuncionario;
import com.sistema.gerenciamento.hospitalar.exceptions.NotFoundException;
import com.sistema.gerenciamento.hospitalar.models.EnderecoModel;
import com.sistema.gerenciamento.hospitalar.models.FuncionarioModel;
import com.sistema.gerenciamento.hospitalar.models.MedicoModel;
import com.sistema.gerenciamento.hospitalar.repositorys.EnderecoRepository;
import com.sistema.gerenciamento.hospitalar.repositorys.FuncionarioRepository;
import com.sistema.gerenciamento.hospitalar.repositorys.MedicoRepository;
import com.sistema.gerenciamento.hospitalar.repositorys.UsuarioRepository;
import com.sistema.gerenciamento.hospitalar.services.EnderecoService;
import com.sistema.gerenciamento.hospitalar.services.FuncionarioService;
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
public class FuncionarioServiceImpl implements FuncionarioService {

    // Repositórios responsáveis pela manipulação dos dados
    final FuncionarioRepository funcionarioRepository;
    final UsuarioRepository usuarioRepository;
    final EnderecoRepository enderecoRepository;
    final MedicoRepository medicoRepository;

    // Construtor da classe que recebe os repositórios necessários
    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository, UsuarioRepository usuarioRepository, EnderecoService enderecoService, EnderecoRepository enderecoRepository, MedicoRepository medicoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.medicoRepository = medicoRepository;
    }

    /**
     * Método para buscar um funcionário por ID.
     *
     * @param funcionarioId ID do funcionário a ser buscado.
     * @return Optional<FuncionarioModel> Funcionário encontrado.
     * @throws NotFoundException Se o funcionário não for encontrado, lança uma exceção.
     */
    @Override
    public Optional<FuncionarioModel> findById(UUID funcionarioId) {
        Optional<FuncionarioModel> funcionarioModelOptional = funcionarioRepository.findById(funcionarioId);

        // Tratativa de erro caso o funcionário não seja encontrado
        if (funcionarioModelOptional.isEmpty()){
            throw new NotFoundException("ERRO, funcionario não encontrado!");
        }
        return funcionarioModelOptional;
    }

    /**
     * Método para verificar se já existe um funcionário com o nome completo informado.
     *
     * @param nomeCompleto Nome completo do funcionário a ser verificado.
     * @return boolean Retorna true se existir, caso contrário, false.
     */
    @Override
    public boolean existByNomeCompleto(String nomeCompleto) {
        return funcionarioRepository.existsByNomeCompleto(nomeCompleto);
    }

    /**
     * Método para verificar se já existe um funcionário com o CPF informado.
     *
     * @param cpf CPF do funcionário a ser verificado.
     * @return boolean Retorna true se existir, caso contrário, false.
     */
    @Override
    public boolean existByCpf(String cpf) {
        return funcionarioRepository.existsByCpf(cpf);
    }

    /**
     * Método para salvar um novo funcionário.
     *
     * @param funcionarioRecordDto Dados do funcionário recebidos do front-end.
     * @return FuncionarioModel Funcionário salvo no banco de dados.
     */
    @Override
    public FuncionarioModel saveFuncionario(FuncionarioRecordDto funcionarioRecordDto) {
        var funcionarioModel = new FuncionarioModel();

        // Copia os dados do DTO para o modelo de funcionário
        BeanUtils.copyProperties(funcionarioRecordDto, funcionarioModel);

        // Define as datas de cadastro e de atualização
        funcionarioModel.setDataCadastro(LocalDateTime.now(ZoneId.of("America/Recife")));
        funcionarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));
        // Define o status do funcionário como ativo
        funcionarioModel.setStatusFuncionario(StatusFuncionario.ATIVO);

        // Salva o funcionário no banco de dados
        return funcionarioRepository.save(funcionarioModel);
    }

    /**
     * Método para verificar se já existe um funcionário com o RG informado.
     *
     * @param rg RG do funcionário a ser verificado.
     * @return boolean Retorna true se existir, caso contrário, false.
     */
    @Override
    public boolean existByRg(String rg) {
        return funcionarioRepository.existsByRg(rg);
    }

    /**
     * Método para listar todos os funcionários com filtros e paginação.
     *
     * @param spec Especificação de filtros a serem aplicados na consulta.
     * @param pageable Informações sobre a paginação.
     * @return Page<FuncionarioModel> Uma página de funcionários.
     */
    @Override
    public Page<FuncionarioModel> fidAll(Specification<FuncionarioModel> spec, Pageable pageable) {
        // Retorna a página de funcionários com filtros e paginação aplicados
        return funcionarioRepository.findAll(spec, pageable);
    }

    /**
     * Método para excluir um funcionário e todos os dados associados a ele.
     *
     * @param funcionarioModel Funcionário a ser excluído.
     */
    @Transactional
    @Override
    public void deleteFuncionario(FuncionarioModel funcionarioModel) {
        UUID funcionarioId = funcionarioModel.getFuncionarioId();

        // Excluir usuário associado ao funcionário antes de excluir o funcionário
        if (funcionarioModel.getUsuario() != null) {
            usuarioRepository.delete(funcionarioModel.getUsuario());
        }

        // Excluir médico associado ao funcionário
        Optional<MedicoModel> medicoModel = medicoRepository.findByFuncionarioMedicoFuncionarioId(funcionarioId);
        medicoModel.ifPresent(medicoRepository::delete);

        // Excluir endereço associado ao funcionário
        Optional<EnderecoModel> enderecoModel = enderecoRepository.findByFuncionarioEnderecoFuncionarioId(funcionarioId);
        enderecoModel.ifPresent(enderecoRepository::delete);

        // Exclui o funcionário
        funcionarioRepository.delete(funcionarioModel);
    }

    /**
     * Método para atualizar as informações de um funcionário.
     *
     * @param funcionarioRecordDto Dados atualizados do funcionário.
     * @param funcionarioModel Funcionário a ser atualizado.
     * @return FuncionarioModel Funcionário atualizado no banco de dados.
     */
    @Override
    public FuncionarioModel updateFuncionario(FuncionarioRecordDto funcionarioRecordDto, FuncionarioModel funcionarioModel) {
        // Atualiza a data de atualização
        funcionarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        // Atualiza os outros campos do funcionário
        funcionarioModel.setTelefone(funcionarioRecordDto.telefone());
        funcionarioModel.setFuncao(funcionarioRecordDto.funcao());

        // Salva as alterações no banco de dados e retorna o funcionário atualizado
        return funcionarioRepository.save(funcionarioModel);
    }

    /**
     * Método para atualizar o status de um funcionário.
     *
     * @param funcionarioModel Funcionário cujo status será atualizado.
     * @param funcionarioRecordDto Dados do status do funcionário.
     * @return FuncionarioModel Funcionário com o status atualizado.
     */
    @Override
    public FuncionarioModel updateStatusFuncionario(FuncionarioModel funcionarioModel, FuncionarioRecordDto funcionarioRecordDto) {
        // Atualiza o status do funcionário com o novo status
        funcionarioModel.setStatusFuncionario(funcionarioRecordDto.statusFuncionario());
        // Salva as alterações e retorna o funcionário com o status atualizado
        return funcionarioRepository.save(funcionarioModel);
    }
}