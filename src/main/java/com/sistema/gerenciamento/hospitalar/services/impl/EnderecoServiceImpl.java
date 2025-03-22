package com.sistema.gerenciamento.hospitalar.services.impl;

import com.sistema.gerenciamento.hospitalar.dtos.EnderecoRecordDto;
import com.sistema.gerenciamento.hospitalar.exceptions.NotFoundException;
import com.sistema.gerenciamento.hospitalar.models.EnderecoModel;
import com.sistema.gerenciamento.hospitalar.models.FuncionarioModel;
import com.sistema.gerenciamento.hospitalar.models.PacienteModel;
import com.sistema.gerenciamento.hospitalar.repositorys.EnderecoRepository;
import com.sistema.gerenciamento.hospitalar.services.EnderecoService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    // Repositório responsável pela manipulação de dados de endereço
    final EnderecoRepository enderecoRepository;

    // Construtor da classe que recebe o repositório de Endereço
    public EnderecoServiceImpl(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    /**
     * Método para salvar um endereço de um funcionário.
     *
     * @param enderecoRecordDto Dados do endereço recebidos do front-end.
     * @param funcionarioModel Modelo do funcionário relacionado ao endereço.
     * @return EnderecoModel Objeto de endereço salvo no banco de dados.
     */
    @Override
    public EnderecoModel saveEnderecoFuncionario(EnderecoRecordDto enderecoRecordDto, FuncionarioModel funcionarioModel) {
        var enderecoModel = new EnderecoModel();

        // Copia as propriedades do DTO para o modelo de endereço
        BeanUtils.copyProperties(enderecoRecordDto, enderecoModel);

        // Define as datas de cadastro e atualização do endereço
        enderecoModel.setDataCadastro(LocalDateTime.now(ZoneId.of("America/Recife")));
        enderecoModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        // Associa o funcionário ao endereço
        enderecoModel.setFuncionarioEndereco(funcionarioModel);

        // Salva o endereço no repositório
        return enderecoRepository.save(enderecoModel);
    }

    /**
     * Método para salvar um endereço de um paciente.
     *
     * @param enderecoRecordDto Dados do endereço recebidos do front-end.
     * @param pacienteModel Modelo do paciente relacionado ao endereço.
     * @return EnderecoModel Objeto de endereço salvo no banco de dados.
     */
    @Override
    public EnderecoModel saveEnderecoPaciente(EnderecoRecordDto enderecoRecordDto, PacienteModel pacienteModel) {
        var enderecoModel = new EnderecoModel();

        // Copia as propriedades do DTO para o modelo de endereço
        BeanUtils.copyProperties(enderecoRecordDto, enderecoModel);

        // Define as datas de cadastro e atualização do endereço
        enderecoModel.setDataCadastro(LocalDateTime.now(ZoneId.of("America/Recife")));
        enderecoModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        // Associa o paciente ao endereço
        enderecoModel.setPacienteEndereco(pacienteModel);

        // Salva o endereço no repositório
        return enderecoRepository.save(enderecoModel);
    }

    /**
     * Método para listar todos os endereços com paginação e filtro.
     *
     * @param spec Especificação de filtros a serem aplicados na consulta.
     * @param pageable Objetos que contém informações sobre paginação.
     * @return Page<EnderecoModel> Uma página de endereços.
     */
    @Override
    public Page<EnderecoModel> fidAll(Specification<EnderecoModel> spec, Pageable pageable) {
        // Retorna a página de endereços com os filtros e paginação aplicados
        return enderecoRepository.findAll(spec, pageable);
    }

    /**
     * Método para buscar um endereço por ID.
     *
     * @param enderecoId ID do endereço a ser buscado.
     * @return Optional<EnderecoModel> Endereço encontrado.
     * @throws NotFoundException Se o endereço não for encontrado, lança uma exceção.
     */
    @Override
    public Optional<EnderecoModel> findById(UUID enderecoId) {
        Optional<EnderecoModel> enderecoModelOptional = enderecoRepository.findById(enderecoId);

        // Tratativa de erro caso o endereço não seja encontrado
        if (enderecoModelOptional.isEmpty()){
            throw new NotFoundException("ERRO, endereço não encontrado!");
        }
        return enderecoModelOptional;
    }

    /**
     * Método para atualizar um endereço existente.
     *
     * @param enderecoRecordDto Dados atualizados do endereço.
     * @param enderecoModel Endereço a ser atualizado.
     * @return EnderecoModel Endereço atualizado.
     */
    @Override
    public EnderecoModel updateEndereco(EnderecoRecordDto enderecoRecordDto, EnderecoModel enderecoModel) {
        // Atualiza os campos do endereço com os dados do DTO
        enderecoModel.setLogradouro(enderecoRecordDto.logradouro());
        enderecoModel.setCep(enderecoRecordDto.cep());
        enderecoModel.setBairro(enderecoRecordDto.bairro());
        enderecoModel.setLocalidade(enderecoRecordDto.localidade());
        enderecoModel.setNumeroCasa(enderecoRecordDto.numeroCasa());
        enderecoModel.setComplemento(enderecoRecordDto.complemento());
        enderecoModel.setUf(enderecoRecordDto.uf());

        // Salva as alterações no banco de dados
        return enderecoRepository.save(enderecoModel);
    }

    /**
     * Método para excluir um endereço.
     *
     * @param enderecoModel Endereço a ser excluído.
     */
    @Override
    public void deleteEndereco(EnderecoModel enderecoModel) {
        // Exclui o endereço do repositório
        enderecoRepository.delete(enderecoModel);
    }
}