package com.sistema.gerenciamento.hospitalar.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sistema.gerenciamento.hospitalar.enums.Especialidade;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Classe que representa o modelo da entidade "Medico".
 * Esta classe é mapeada para a tabela "TB_MEDICO" no banco de dados.
 * A classe implementa Serializable para garantir que os objetos possam ser serializados.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Iclua apenas valores que não sejam nulos
@Entity
@Table(name = "TB_MEDICO")
public class MedicoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //
    private UUID medicoId;

    @Column(nullable = false, unique = true, length = 12)
    private String crm;

    @Column(nullable = false, unique = true, length = 10)
    private String cns;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Column(nullable = false, length = 10)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime dataCadastro;

    @Column(nullable = false, length = 10)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "funcionario_id")
    private FuncionarioModel funcionarioMedico;

    @OneToMany(mappedBy = "medicoConsulta", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ConsultaModel> consultas;

    public UUID getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(UUID medicoId) {
        this.medicoId = medicoId;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getCns() {
        return cns;
    }

    public void setCns(String cns) {
        this.cns = cns;
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

    public FuncionarioModel getFuncionarioMedico() {
        return funcionarioMedico;
    }

    public void setFuncionarioMedico(FuncionarioModel funcionarioMedico) {
        this.funcionarioMedico = funcionarioMedico;
    }

    public List<ConsultaModel> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<ConsultaModel> consultas) {
        this.consultas = consultas;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
}