package com.sistema.gerenciamento.hospitalar.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Classe que representa o modelo da entidade "Clinica Hospitalar".
 * Esta classe é mapeada para a tabela "TB_CLINICA_HOSPITALAR" no banco de dados.
 * A classe implementa Serializable para garantir que os objetos possam ser serializados.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_CLINICA_HOSPITALAR")
public class ClinicaHospitalarModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID clinicaHospitlarId;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 14, unique = true)
    private String cnpj;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false, length = 10)
    private String telefoneCelular;

    @OneToOne(mappedBy = "clinicaEndereco", fetch = FetchType.LAZY)
    private EnderecoModel EnderecoId;

    @OneToMany(mappedBy = "clinicaConsulta", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ConsultaModel> consultas;

    @Column(nullable = false, length = 10)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss") // Padrão de data a niivel de atribulto
    private LocalDateTime dataCadastro;

    @Column(nullable = false, length = 10)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    public UUID getClinicaHospitlarId() {
        return clinicaHospitlarId;
    }

    public void setClinicaHospitlarId(UUID clinicaHospitlarId) {
        this.clinicaHospitlarId = clinicaHospitlarId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public EnderecoModel getEnderecoId() {
        return EnderecoId;
    }

    public void setEnderecoId(EnderecoModel enderecoId) {
        EnderecoId = enderecoId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<ConsultaModel> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<ConsultaModel> consultas) {
        this.consultas = consultas;
    }
}
