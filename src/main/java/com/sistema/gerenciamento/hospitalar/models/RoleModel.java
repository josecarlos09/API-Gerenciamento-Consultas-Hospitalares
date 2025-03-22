package com.sistema.gerenciamento.hospitalar.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sistema.gerenciamento.hospitalar.enums.RoleType;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.UUID;

/**
 * Classe que representa o modelo da entidade "Role".
 * Esta classe é mapeada para a tabela "TB_ROLE" no banco de dados.
 * A classe implementa Serializable para garantir que os objetos possam ser serializados.
 */
@Entity
@Table(name = "TB_ROLE")
public class RoleModel implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID roleId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 30)
    private RoleType roleNome;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return this.roleNome.toString();
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public RoleType getRoleNome() {
        return roleNome;
    }

    public void setRoleNome(RoleType roleNome) {
        this.roleNome = roleNome;
    }
}
