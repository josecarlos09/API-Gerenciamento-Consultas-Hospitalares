package com.sistema.gerenciamento.hospitalar.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe que representa o modelo da entidade "Endereco".
 * Esta classe é mapeada para a tabela "TB_ENDERECO" no banco de dados.
 * A classe implementa Serializable para garantir que os objetos possam ser serializados.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Iclua apenas valores que não sejam nulos
@Entity
@Table(name = "TB_ENDERECO")
public class EnderecoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //
    private UUID enderecoId;

    @Column(length = 10)
    private String cep;

    @Column(nullable = false, length = 30)
    private String logradouro;

    @Column(length = 30)
    private String Complemento;

    @Column(nullable = false, length = 30)
    private String bairro;

    @Column(nullable = false, length = 30)
    private String localidade;

    @Column(nullable = false, length = 30)
    private String uf;

    @Column(length = 30)
    private String numeroCasa;

    @Column(nullable = false, length = 10)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss") // Padrão de data a niivel de atribulto
    private LocalDateTime dataCadastro;

    @Column(nullable = false, length = 10)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "funcionario_id") // Esta tabela terá a chave estrangeira
    private FuncionarioModel funcionarioEndereco;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "paciente_id") // Esta tabela terá a chave estrangeira
    private PacienteModel pacienteEndereco;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "clinica_id") // Esta tabela terá a chave estrangeira
    private ClinicaHospitalarModel clinicaEndereco;

    public UUID getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(UUID enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
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

    public FuncionarioModel getFuncionarioEndereco() {
        return funcionarioEndereco;
    }

    public void setFuncionarioEndereco(FuncionarioModel funcionarioEndereco) {
        this.funcionarioEndereco = funcionarioEndereco;
    }

    public PacienteModel getPacienteEndereco() {
        return pacienteEndereco;
    }

    public void setPacienteEndereco(PacienteModel pacienteEndereco) {
        this.pacienteEndereco = pacienteEndereco;
    }

    public ClinicaHospitalarModel getClinicaEndereco() {
        return clinicaEndereco;
    }

    public void setClinicaEndereco(ClinicaHospitalarModel clinicaEndereco) {
        this.clinicaEndereco = clinicaEndereco;
    }
}