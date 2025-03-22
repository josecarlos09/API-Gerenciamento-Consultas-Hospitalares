package com.sistema.gerenciamento.hospitalar.services.impl;

import com.sistema.gerenciamento.hospitalar.dtos.ConsultaRecordDto;
import com.sistema.gerenciamento.hospitalar.enums.StatusAtendimento;
import com.sistema.gerenciamento.hospitalar.enums.StatusPaciente;
import com.sistema.gerenciamento.hospitalar.exceptions.NotFoundException;
import com.sistema.gerenciamento.hospitalar.models.ClinicaHospitalarModel;
import com.sistema.gerenciamento.hospitalar.models.ConsultaModel;
import com.sistema.gerenciamento.hospitalar.models.MedicoModel;
import com.sistema.gerenciamento.hospitalar.models.PacienteModel;
import com.sistema.gerenciamento.hospitalar.repositorys.ClinicaHospitalarRepository;
import com.sistema.gerenciamento.hospitalar.repositorys.ConsultaRepository;
import com.sistema.gerenciamento.hospitalar.repositorys.MedicoRepository;
import com.sistema.gerenciamento.hospitalar.repositorys.PacienteRepository;
import com.sistema.gerenciamento.hospitalar.services.ConsultaService;
import com.sistema.gerenciamento.hospitalar.validates.consultaMedica.ValidadorAgendamentoDeConsulta;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    // Repositórios para interagir com as entidades
    final PacienteRepository pacienteRepository;
    final MedicoRepository medicoRepository;
    final ConsultaRepository consultaRepository;
    final ClinicaHospitalarRepository clinicaHospitalarRepository;
    final List<ValidadorAgendamentoDeConsulta> validarConsultaMedica;

    // Construtor para injeção de dependências
    public ConsultaServiceImpl(PacienteRepository pacienteRepository, MedicoRepository medicoRepository, ConsultaRepository consultaRepository, ClinicaHospitalarRepository clinicaHospitalarRepository, List<ValidadorAgendamentoDeConsulta> validarConsultaMedica) {
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.consultaRepository = consultaRepository;
        this.clinicaHospitalarRepository = clinicaHospitalarRepository;
        this.validarConsultaMedica = validarConsultaMedica;
    }

    /**
     * Salva uma nova consulta após validar as informações fornecidas.
     *
     * @param consultaRecordDto Dados da consulta a ser criada.
     * @param medicoModel Modelo de médico associado à consulta.
     * @param pacienteModel Modelo de paciente associado à consulta.
     * @param clinicaHospitalarModel Modelo da clínica hospitalar associada à consulta.
     * @return ConsultaModel A consulta salva no banco de dados.
     */
    @Override
    public ConsultaModel saveConsulta(ConsultaRecordDto consultaRecordDto, MedicoModel medicoModel, PacienteModel pacienteModel, ClinicaHospitalarModel clinicaHospitalarModel) {
        var consultaModel = new ConsultaModel();
        // Copia as propriedades do DTO para o modelo de consulta
        BeanUtils.copyProperties(consultaRecordDto, consultaModel);

        // Valida se o paciente existe
        if (!pacienteRepository.existsById(pacienteModel.getPacienteId())) {
            throw new NotFoundException("Paciente não encontrado!");
        }

        // Valida se o paciente está ativo
        if (pacienteModel.getStatusPaciente() == StatusPaciente.INATIVO) {
            throw new NotFoundException("O paciente está INATIVO!");
        }

        // Valida se o paciente já tem uma consulta agendada no mesmo dia
        var primeiroHorario = consultaRecordDto.dataConsulta().withHour(7);
        var ultimoHorario = consultaRecordDto.dataConsulta().withHour(18);
        var pacientePossuiOutraConsultaNoDia = consultaRepository.existsByPacienteConsultaPacienteIdAndDataConsultaBetween(pacienteModel.getPacienteId(), primeiroHorario, ultimoHorario);
        if (pacientePossuiOutraConsultaNoDia) {
            throw new NotFoundException("Esse paciente já possui uma consulta agendada para esse dia nesse mesmo horário");
        }

        // Valida se o médico existe
        if (!medicoRepository.existsById(medicoModel.getMedicoId())) {
            throw new NotFoundException("Médico não encontrado!");
        }

        // Valida se o médico tem especialização
        if (medicoModel.getEspecialidade() == null) {
            throw new NotFoundException("O médico deve ter uma especialização!");
        }

        // Valida se o médico já tem outra consulta no mesmo horário
        var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoConsultaMedicoIdAndDataConsultaAndMotivoCancelamentoIsNull(medicoModel.getMedicoId(), consultaModel.getDataConsulta());
        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new NotFoundException("Esse médico já possui uma consulta agendada para esse mesmo dia e horário");
        }

        // Valida se a clínica hospitalar existe
        if (!clinicaHospitalarRepository.existsById(clinicaHospitalarModel.getClinicaHospitlarId())) {
            throw new NotFoundException("Clínica hospitalar não encontrada!");
        }

        // Chama a validação personalizada para a consulta
        validarConsultaMedica.forEach(v -> v.validar(consultaRecordDto));

        // Preenche as informações de data de cadastro e atualização da consulta
        consultaModel.setDataCadastro(LocalDateTime.now(ZoneId.of("America/Recife")));
        consultaModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));
        consultaModel.setStatusAtendimento(StatusAtendimento.MARCADO);
        consultaModel.setMedicoConsulta(medicoModel);
        consultaModel.setPacienteConsulta(pacienteModel);
        consultaModel.setClinicaConsulta(clinicaHospitalarModel);
        consultaModel.setDataConsulta(consultaRecordDto.dataConsulta());

        // Salva a consulta no repositório
        return consultaRepository.save(consultaModel);
    }

    /**
     * Retorna todas as consultas paginadas com base nas especificações fornecidas.
     *
     * @param spec Especificação de consulta.
     * @param pageable Informações de paginação.
     * @return Page<ConsultaModel> Página de consultas.
     */
    @Override
    public Page<ConsultaModel> fidAll(Specification<ConsultaModel> spec, Pageable pageable) {
        return consultaRepository.findAll(spec, pageable);
    }

    /**
     * Retorna uma consulta pelo seu ID.
     *
     * @param consultaId ID da consulta.
     * @return Optional<ConsultaModel> Consulta encontrada, se existir.
     */
    @Override
    public Optional<ConsultaModel> findById(UUID consultaId) {
        return consultaRepository.findById(consultaId);
    }

    /**
     * Finaliza uma consulta médica, atualizando o status e o resultado.
     *
     * @param consultaRecordDto Dados da consulta a ser finalizada.
     * @param consultaModel Modelo da consulta.
     * @return ConsultaModel A consulta atualizada.
     */
    @Override
    public ConsultaModel finalizarConsultaMedica(ConsultaRecordDto consultaRecordDto, ConsultaModel consultaModel) {
        consultaModel.setStatusAtendimento(consultaRecordDto.statusAtendimento());
        consultaModel.setResultadoConsulta(consultaRecordDto.resultadoConsulta());
        consultaModel.setObservacao(consultaRecordDto.observacao());

        return consultaRepository.save(consultaModel);
    }

    /**
     * Exclui uma consulta do banco de dados.
     *
     * @param consultaModel Modelo da consulta a ser excluída.
     */
    @Override
    public void deleteByConsultaId(ConsultaModel consultaModel) {
        consultaRepository.delete(consultaModel);
    }

    /**
     * Atualiza os dados de uma consulta médica.
     *
     * @param consultaRecordDto Dados da consulta a ser atualizada.
     * @param consultaModel Modelo da consulta a ser atualizada.
     * @return ConsultaModel A consulta atualizada.
     */
    @Override
    public ConsultaModel atualizarConsultaMedica(ConsultaRecordDto consultaRecordDto, ConsultaModel consultaModel) {
        consultaModel.setDataConsulta(consultaRecordDto.dataConsulta());
        consultaModel.setValorConsulta(consultaRecordDto.valorConsulta());
        consultaModel.setObservacao(consultaRecordDto.observacao());
        consultaModel.setResultadoConsulta(consultaRecordDto.resultadoConsulta());

        return consultaRepository.save(consultaModel);
    }

    /**
     * Verifica se já existe uma consulta agendada para o médico.
     *
     * @param medicoId ID do médico.
     * @return boolean Retorna verdadeiro se existir uma consulta agendada para o médico.
     */
    @Override
    public boolean existsByMedicoConsultaMedicoId(UUID medicoId) {
        return consultaRepository.existsByMedicoConsultaMedicoId(medicoId);
    }

    /**
     * Verifica se já existe uma consulta agendada para o paciente.
     *
     * @param pacienteId ID do paciente.
     * @return boolean Retorna verdadeiro se existir uma consulta agendada para o paciente.
     */
    @Override
    public boolean existsByPacienteConsultaPacienteId(UUID pacienteId) {
        return consultaRepository.existsByPacienteConsultaPacienteId(pacienteId);
    }

    /**
     * Verifica se já existe uma consulta agendada para a clínica hospitalar.
     *
     * @param clinicaId ID da clínica hospitalar.
     * @return boolean Retorna verdadeiro se existir uma consulta agendada para a clínica hospitalar.
     */
    @Override
    public boolean existsByClinicaConsultaClinicaHospitalarId(UUID clinicaId) {
        return consultaRepository.existsByClinicaConsultaClinicaHospitlarId(clinicaId);
    }
}