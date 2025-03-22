package com.sistema.gerenciamento.hospitalar.configs.security;

import com.sistema.gerenciamento.hospitalar.models.UsuarioModel;
import com.sistema.gerenciamento.hospitalar.repositorys.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {
    final UsuarioRepository usuarioRepository;

    public UsuarioDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Método para carregar um usuário pelo nome de usuário.
     * Esse método é chamado pelo Spring Security durante a autenticação.
     * @param username Nome de usuário recebido na autenticação
     * @return UserDetails contendo informações do usuário autenticado
     * @throws UsernameNotFoundException se o usuário não for encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo nome
        UsuarioModel usuarioModel = usuarioRepository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // Converte a entidade UsuarioModel para um objeto UserDetails e retorna
        return UsuarioDetailsImpl.build(usuarioModel);
    }
}