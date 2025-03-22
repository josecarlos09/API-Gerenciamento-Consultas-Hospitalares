package com.sistema.gerenciamento.hospitalar.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sistema.gerenciamento.hospitalar.enums.StatusUsuario;
import com.sistema.gerenciamento.hospitalar.enums.UsuarioType;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Classe que representa o modelo da entidade "Usuario".
 * Esta classe é mapeada para a tabela "TB_USUARIO" no banco de dados.
 * A classe implementa Serializable para garantir que os objetos possam ser serializados.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Iclua apenas valores que não sejam nulos.
@Entity
@Table(name = "TB_USUARIO")
public class UsuarioModel extends RepresentationModel<UsuarioModel>  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //
    private UUID usuarioId;

    @Column(nullable = false, unique = true, length = 10)
    private String codigoUsuario;

    @Column(nullable = false, unique = true, length = 150)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusUsuario statusUsuario;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UsuarioType usuarioType;

    @JsonIgnore
    @Column(nullable = false, unique = true, length = 255)
    private String senha;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @OneToOne
    @JoinColumn(name = "funcionario_id") // Define a chave estrangeira corretamente
    @JsonIgnore // Evitar loops de serialização
    private FuncionarioModel funcionarioUsuario;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Acesso apenas escrita
    @ManyToMany(fetch = FetchType.LAZY) // Associação de muitos para muitos
    // Será gerado uma tabela auxiliar TB_USUARIO_ROLE que conterar o id de usuario e o id de role
    @JoinTable(name = "TB_USUARIO_ROLE",
               joinColumns = @JoinColumn(name = "usuarioId"),
               inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<RoleModel> roles = new HashSet<>(); // Estrutura de Set

    // Métodos acessores e modificadores (GETs e SETs)
    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public StatusUsuario getStatusUsuario() {
        return statusUsuario;
    }

    public void setStatusUsuario(StatusUsuario statusUsuario) {
        this.statusUsuario = statusUsuario;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public FuncionarioModel getFuncionarioUsuario() {
        return funcionarioUsuario;
    }

    public void setFuncionarioUsuario(FuncionarioModel funcionarioUsuario) {
        this.funcionarioUsuario = funcionarioUsuario;
    }

    public Set<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
    }

    public UsuarioType getUsuarioType() {
        return usuarioType;
    }

    public void setUsuarioType(UsuarioType usuarioType) {
        this.usuarioType = usuarioType;
    }
}
