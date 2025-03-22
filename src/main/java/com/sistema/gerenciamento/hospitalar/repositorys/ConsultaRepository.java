package com.sistema.gerenciamento.hospitalar.repositorys;

import com.sistema.gerenciamento.hospitalar.models.ConsultaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.UUID;

// Define a interface do repositório para a entidade ConsultaModel
public interface ConsultaRepository extends JpaRepository<ConsultaModel, UUID>, JpaSpecificationExecutor<ConsultaModel> {

    // Método para verificar se já existe uma consulta agendada com o médico, na data fornecida, e sem motivo de cancelamento
    boolean existsByMedicoConsultaMedicoIdAndDataConsultaAndMotivoCancelamentoIsNull(UUID medicoId, LocalDateTime dataConsulta);

    // Método para verificar se já existe uma consulta agendada com o paciente no intervalo de horários fornecido
    boolean existsByPacienteConsultaPacienteIdAndDataConsultaBetween(UUID pacienteId, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    // Método para verificar se já existe uma consulta agendada com o médico fornecido
    boolean existsByMedicoConsultaMedicoId(UUID medicoId);

    // Método para verificar se já existe uma consulta agendada com o paciente fornecido
    boolean existsByPacienteConsultaPacienteId(UUID pacienteId);

    // Método para verificar se já existe uma consulta agendada na clínica fornecida
    boolean existsByClinicaConsultaClinicaHospitlarId(UUID clinicaId);
}
