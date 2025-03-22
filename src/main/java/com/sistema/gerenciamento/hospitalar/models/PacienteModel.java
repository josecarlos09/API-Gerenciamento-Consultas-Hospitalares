package com.sistema.gerenciamento.hospitalar.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sistema.gerenciamento.hospitalar.enums.Genero;
import com.sistema.gerenciamento.hospitalar.enums.StatusPaciente;
import com.sistema.gerenciamento.hospitalar.enums.TipoSanguineo;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Classe que representa o modelo da entidade "Paciente".
 * Esta classe é mapeada para a tabela "TB_PACIENTE" no banco de dados.
 * A classe implementa Serializable para garantir que os objetos possam ser serializados.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_PACIENTE")
public class PacienteModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pacienteId;

    @Column(nullable = false, unique = true, length = 50)
    private String nomeCompleto;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true, length = 10)
    private String rg;

    @Column(unique = true, length = 14)
    private String telefoneCelular;

    @Column(nullable = false, length = 10)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(length = 12, nullable = false, unique = true)
    private String cns;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSanguineo tipoSanguineo;

    @Column(length = 30)
    private String nacionalidade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPaciente statusPaciente;

    @Column(nullable = false, length = 10)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss") // Padrão de data a niivel de atribulto
    private LocalDateTime dataCadastro;

    @Column(nullable = false, length = 10)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    @OneToOne(mappedBy = "pacienteEndereco", fetch = FetchType.LAZY)
    private EnderecoModel EnderecoId;

    @OneToMany(mappedBy = "pacienteConsulta", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ConsultaModel> consulta;

    public UUID getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(UUID parcienteId) {
        this.pacienteId = parcienteId;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getCns() {
        return cns;
    }

    public void setCns(String cns) {
        this.cns = cns;
    }

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public StatusPaciente getStatusPaciente() {
        return statusPaciente;
    }

    public void setStatusPaciente(StatusPaciente statusPaciente) {
        this.statusPaciente = statusPaciente;
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

    public EnderecoModel getEnderecoId() {
        return EnderecoId;
    }

    public void setEnderecoId(EnderecoModel enderecoId) {
        EnderecoId = enderecoId;
    }

    public List<ConsultaModel> getConsulta() {
        return consulta;
    }

    public void setConsulta(List<ConsultaModel> consulta) {
        this.consulta = consulta;
    }
}
