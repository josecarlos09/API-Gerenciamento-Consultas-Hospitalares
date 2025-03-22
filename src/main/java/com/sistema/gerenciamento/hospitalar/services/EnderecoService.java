package com.sistema.gerenciamento.hospitalar.services;

import com.sistema.gerenciamento.hospitalar.dtos.EnderecoRecordDto;
import com.sistema.gerenciamento.hospitalar.models.EnderecoModel;
import com.sistema.gerenciamento.hospitalar.models.FuncionarioModel;
import com.sistema.gerenciamento.hospitalar.models.PacienteModel;
import com.sistema.gerenciamento.hospitalar.specifications.SpecificationsTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface EnderecoService {

    // Método para salvar o endereço de um funcionário. Recebe um DTO com os dados do endereço e o modelo do funcionário.
    // O método associará o endereço ao funcionário e salvará as informações no banco de dados.
    EnderecoModel saveEnderecoFuncionario(EnderecoRecordDto enderecoRecordDto, FuncionarioModel funcionarioModel);

    // Método para salvar o endereço de um paciente. Recebe um DTO com os dados do endereço e o modelo do paciente.
    // Similar ao método acima, este irá associar o endereço ao paciente e salvar as informações.
    EnderecoModel saveEnderecoPaciente(EnderecoRecordDto enderecoRecordDto, PacienteModel pacienteModel);

    // Método para buscar todos os endereços com suporte a filtros dinâmicos e paginação.
    // O parâmetro 'spec' permite construir consultas dinâmicas e o 'pageable' define a página e o tamanho para paginar os resultados.
    Page<EnderecoModel> fidAll(Specification<EnderecoModel> spec, Pageable pageable);

    // Método para buscar um endereço pelo seu ID único (UUID). Retorna um Optional para evitar NullPointerExceptions caso o endereço não seja encontrado.
    Optional<EnderecoModel> findById(UUID enderecoId);

    // Método para atualizar os dados de um endereço. Recebe o DTO com as novas informações e o modelo do endereço a ser atualizado.
    // Esse método irá atualizar os dados do endereço no banco de dados.
    EnderecoModel updateEndereco(EnderecoRecordDto enderecoRecordDto, EnderecoModel enderecoModel);

    // Método para deletar um endereço específico. Recebe o modelo do endereço a ser excluído e remove o registro do banco de dados.
    void deleteEndereco(EnderecoModel enderecoModel);
}
