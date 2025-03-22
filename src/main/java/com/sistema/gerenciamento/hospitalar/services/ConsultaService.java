package com.sistema.gerenciamento.hospitalar.services;

import com.sistema.gerenciamento.hospitalar.dtos.ConsultaRecordDto;
import com.sistema.gerenciamento.hospitalar.models.ClinicaHospitalarModel;
import com.sistema.gerenciamento.hospitalar.models.ConsultaModel;
import com.sistema.gerenciamento.hospitalar.models.MedicoModel;
import com.sistema.gerenciamento.hospitalar.models.PacienteModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface ConsultaService {

    // Método para salvar uma nova consulta médica. Recebe um DTO com os dados da consulta e os modelos do médico, paciente e clínica.
    // Isso pode envolver salvar informações relacionadas à consulta e também garantir a associação correta entre as entidades.
    ConsultaModel saveConsulta(ConsultaRecordDto consultaRecordDto, MedicoModel medicoModel, PacienteModel pacienteModel, ClinicaHospitalarModel clinicaHospitalarModel);

    // Método para buscar todas as consultas com suporte a filtros dinâmicos e paginação.
    // O parâmetro 'spec' permite construir consultas dinâmicas, e 'pageable' define a página e o tamanho da página para paginar os resultados.
    Page<ConsultaModel> fidAll(Specification<ConsultaModel> spec, Pageable pageable);

    // Método para buscar uma consulta pelo seu ID único (UUID). Retorna um Optional para evitar NullPointerExceptions caso a consulta não seja encontrada.
    Optional<ConsultaModel> findById(UUID consultaId);

    // Método para finalizar uma consulta médica. Recebe o DTO com os dados atualizados e o modelo da consulta que será alterado.
    // Isso pode envolver alterações no status ou no registro da consulta.
    ConsultaModel finalizarConsultaMedica(ConsultaRecordDto consultaRecordDto, ConsultaModel consultaModel);

    // Método para deletar uma consulta específica pelo modelo da consulta.
    void deleteByConsultaId(ConsultaModel consultaModel);

    // Método para atualizar uma consulta médica. Recebe o DTO com os dados atualizados e o modelo da consulta que será modificado.
    ConsultaModel atualizarConsultaMedica(ConsultaRecordDto consultaRecordDto, ConsultaModel consultaModel);

    // Método para verificar se já existe uma consulta para um determinado médico, dado o ID do médico.
    boolean existsByMedicoConsultaMedicoId(UUID medicoId);

    // Método para verificar se já existe uma consulta para um determinado paciente, dado o ID do paciente.
    boolean existsByPacienteConsultaPacienteId(UUID pacienteId);

    // Método para verificar se já existe uma consulta para uma clínica hospitalar específica, dado o ID da clínica.
    boolean existsByClinicaConsultaClinicaHospitalarId(UUID clinicaId);
}