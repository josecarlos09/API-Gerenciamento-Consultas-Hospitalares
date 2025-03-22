package com.sistema.gerenciamento.hospitalar.configs.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sistema.gerenciamento.hospitalar.models.UsuarioModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UsuarioDetailsImpl implements UserDetails {
    private UUID usuarioId;
    private String codigoUsuario;
    private String nome;
    @JsonIgnore
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Construtor da classe UsuarioDetailsImpl.
     * @param usuarioId ID do usuário.
     * @param codigoUsuario Código único do usuário.
     * @param nome Nome do usuário.
     * @param senha Senha do usuário (será ignorada na serialização JSON).
     * @param authorities Lista de autoridades (roles) do usuário.
     */
    public UsuarioDetailsImpl(UUID usuarioId, String codigoUsuario, String nome, String senha, Collection<? extends GrantedAuthority> authorities) {
        this.usuarioId = usuarioId;
        this.codigoUsuario = codigoUsuario;
        this.nome = nome;
        this.senha = senha;
        this.authorities = authorities;
    }

    /**
     * Método para construir um objeto UsuarioDetailsImpl a partir de um UsuarioModel.
     * @param usuarioModel Objeto da entidade UsuarioModel.
     * @return Objeto UsuarioDetailsImpl preenchido.
     */
    public static UsuarioDetailsImpl build(UsuarioModel usuarioModel) {
        List<GrantedAuthority> authorities = usuarioModel.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
        return new UsuarioDetailsImpl(
                usuarioModel.getUsuarioId(),
                usuarioModel.getCodigoUsuario(),
                usuarioModel.getNome(),
                usuarioModel.getSenha(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // A conta não expira.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // A conta não está bloqueada.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // As credenciais não expiram.
    }

    @Override
    public boolean isEnabled() {
        return true; // A conta está ativa.
    }

    // Getters e Setters
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

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
