package com.sistema.gerenciamento.hospitalar.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sistema.gerenciamento.hospitalar.enums.MotivoCancelamento;
import com.sistema.gerenciamento.hospitalar.enums.StatusAtendimento;
import com.sistema.gerenciamento.hospitalar.enums.TipoAtendimento;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe que representa o modelo da entidade "Consulta Médica".
 * Esta classe é mapeada para a tabela "TB_CONSULTA_MEDICA" no banco de dados.
 * A classe implementa Serializable para garantir que os objetos possam ser serializados.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_CONSULTA_MEDICA")
public class ConsultaModel  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID consultaId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAtendimento tipoAtendimento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusAtendimento statusAtendimento;

    @Column(nullable = false)
    private float valorConsulta;

    @Column(length = 10)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime dataConsulta;

    @Column(nullable = false, length = 10)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime dataCadastro;

    @Column(nullable = false, length = 10)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime dataAtualizacao;

    @Column(nullable = false, length = 10)
    private String local;

    @Column(name = "motivo_cancelamento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivoCancelamento;

    @Column(length = 255)
    private String resultadoConsulta;

    @Column(length = 255)
    private String observacao;

    // Relacionamentos
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private MedicoModel medicoConsulta;

    // Relacionamentos
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private PacienteModel pacienteConsulta;

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private ClinicaHospitalarModel clinicaConsulta;

    public UUID getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(UUID consultaId) {
        this.consultaId = consultaId;
    }

    public TipoAtendimento getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public StatusAtendimento getStatusAtendimento() {
        return statusAtendimento;
    }

    public void setStatusAtendimento(StatusAtendimento statusAtendimento) {
        this.statusAtendimento = statusAtendimento;
    }

    public float getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(float valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getResultadoConsulta() {
        return resultadoConsulta;
    }

    public void setResultadoConsulta(String resultadoConsulta) {
        this.resultadoConsulta = resultadoConsulta;
    }

    public MedicoModel getMedicoConsulta() {
        return medicoConsulta;
    }

    public void setMedicoConsulta(MedicoModel medicoConsulta) {
        this.medicoConsulta = medicoConsulta;
    }

    public PacienteModel getPacienteConsulta() {
        return pacienteConsulta;
    }

    public void setPacienteConsulta(PacienteModel pacienteConsulta) {
        this.pacienteConsulta = pacienteConsulta;
    }

    public MotivoCancelamento getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(MotivoCancelamento motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public ClinicaHospitalarModel getClinicaConsulta() {
        return clinicaConsulta;
    }

    public void setClinicaConsulta(ClinicaHospitalarModel clinicaConsulta) {
        this.clinicaConsulta = clinicaConsulta;
    }
}
