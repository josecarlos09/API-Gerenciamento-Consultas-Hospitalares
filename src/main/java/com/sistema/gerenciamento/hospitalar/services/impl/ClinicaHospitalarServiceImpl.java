package com.sistema.gerenciamento.hospitalar.services.impl;

import com.sistema.gerenciamento.hospitalar.dtos.ClinicaHospitalarRecordDto;
import com.sistema.gerenciamento.hospitalar.exceptions.NotFoundException;
import com.sistema.gerenciamento.hospitalar.models.ClinicaHospitalarModel;
import com.sistema.gerenciamento.hospitalar.repositorys.ClinicaHospitalarRepository;
import com.sistema.gerenciamento.hospitalar.repositorys.ConsultaRepository;
import com.sistema.gerenciamento.hospitalar.services.ClinicaHospitalarService;
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

// Classe que implementa a lógica de negócios para a entidade ClinicaHospitalar
@Service
public class ClinicaHospitalarServiceImpl implements ClinicaHospitalarService {

    // Repositórios necessários para persistir dados e realizar consultas
    final ClinicaHospitalarRepository clinicaHospitalarRepository;
    final ConsultaRepository consultaRepository;

    // Construtor para injeção de dependências dos repositórios
    public ClinicaHospitalarServiceImpl(ClinicaHospitalarRepository clinicaHospitalarRepository, ConsultaRepository consultaRepository) {
        this.clinicaHospitalarRepository = clinicaHospitalarRepository;
        this.consultaRepository = consultaRepository;
    }

    // Método para salvar uma nova clínica hospitalar
    @Override
    public ClinicaHospitalarModel saveClinicaHospitalar(ClinicaHospitalarRecordDto clinicaHospitalarRecordDto) {

        // Criando uma nova instância do modelo ClinicaHospitalarModel
        var clinicaHospitalar = new ClinicaHospitalarModel();

        // Copiando propriedades do DTO para o modelo
        BeanUtils.copyProperties(clinicaHospitalarRecordDto, clinicaHospitalar);

        // Definindo valores adicionais para o modelo a partir do DTO
        clinicaHospitalar.setCnpj(clinicaHospitalarRecordDto.cnpj());
        clinicaHospitalar.setNome(clinicaHospitalarRecordDto.nome());
        clinicaHospitalar.setEmail(clinicaHospitalarRecordDto.email());
        clinicaHospitalar.setTelefoneCelular(clinicaHospitalarRecordDto.telefone());

        // Definindo a data de cadastro e atualização da clínica
        clinicaHospitalar.setDataCadastro(LocalDateTime.now(ZoneId.of("America/Recife")));
        clinicaHospitalar.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        // Salvando a clínica hospitalar no repositório e retornando o modelo salvo
        return clinicaHospitalarRepository.save(clinicaHospitalar);
    }

    // Método para obter todas as clínicas hospitalares com suporte a especificações e paginação
    @Override
    public Page<ClinicaHospitalarModel> fidAll(Specification<ClinicaHospitalarModel> spec, Pageable pageable) {
        return clinicaHospitalarRepository.findAll(spec, pageable);
    }

    // Método para encontrar uma clínica hospitalar por ID
    @Override
    public Optional<ClinicaHospitalarModel> findById(UUID clinicaId) {
        Optional<ClinicaHospitalarModel> clinicaHospitalarModel = clinicaHospitalarRepository.findById(clinicaId);

        // Tratativa de erro caso o funcionário não seja encontrado
        if (clinicaHospitalarModel.isEmpty()){
            throw new NotFoundException("ERRO, clinica não encontrado!");
        }
        return clinicaHospitalarModel;
    }

    // Método para atualizar os dados de uma clínica hospitalar existente
    @Override
    public ClinicaHospitalarModel atualizarClinicaHospitalar(ClinicaHospitalarRecordDto clinicaHospitalarRecordDto, ClinicaHospitalarModel clinicaHospitalarModel) {
        // Atualizando a data de atualização
        clinicaHospitalarModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        // Atualizando outros campos com os dados do DTO
        clinicaHospitalarModel.setNome(clinicaHospitalarRecordDto.nome());
        clinicaHospitalarModel.setEmail(clinicaHospitalarRecordDto.email());
        clinicaHospitalarModel.setTelefoneCelular(clinicaHospitalarRecordDto.telefone());

        // Salvando e retornando a clínica hospitalar atualizada
        return clinicaHospitalarRepository.save(clinicaHospitalarModel);
    }

    // Método para deletar uma clínica hospitalar por ID, com transação
    @Transactional
    @Override
    public void deleteByClinicaId(ClinicaHospitalarModel clinicaHospitalarModel) {
        clinicaHospitalarRepository.delete(clinicaHospitalarModel);
    }

    // Método para verificar se já existe uma clínica hospitalar com o nome fornecido
    @Override
    public boolean existByNome(String nome) {
        return clinicaHospitalarRepository.existsByNome(nome);
    }

    // Método para verificar se já existe uma clínica hospitalar com o CNPJ fornecido
    @Override
    public boolean existByCnpj(String cnpj) {
        return clinicaHospitalarRepository.existsByCnpj(cnpj);
    }

    // Método para verificar se já existe uma clínica hospitalar com o email fornecido
    @Override
    public boolean existByEmail(String email) {
        return clinicaHospitalarRepository.existsByEmail(email);
    }
}