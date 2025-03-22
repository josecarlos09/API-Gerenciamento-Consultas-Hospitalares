package com.sistema.gerenciamento.hospitalar.configs.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Implementação do filtro de autenticação JWT.
 *
 * <p>Esta classe estende {@link OncePerRequestFilter} para garantir que cada requisição HTTP seja filtrada
 * apenas uma vez. O objetivo é extrair, ValidadorHorarioAntecedencia e processar o token JWT enviado pelo cliente.</p>
 *
 * <p>Se o token JWT for válido, a autenticação do usuário é configurada no contexto de segurança do Spring.</p>
 *
 * @author [Seu Nome]
 * @version 1.0
 * @since [Data]
 */
public class AutenticacaoJwtFilter extends OncePerRequestFilter {

    Logger logger = LogManager.getLogger(AutenticacaoJwtFilter.class);

    final TokenJwt tokenJwt;
    final UsuarioDetailsServiceImpl usuarioDetailsService;

    /**
     * Construtor para inicializar os serviços necessários para autenticação JWT.
     *
     * @param tokenJwt       Serviço para manipulação de JWT.
     * @param userDetailsService Serviço para carregar detalhes do usuário.
     */
    public AutenticacaoJwtFilter(TokenJwt tokenJwt, UsuarioDetailsServiceImpl userDetailsService) {
        this.tokenJwt = tokenJwt;
        this.usuarioDetailsService = userDetailsService;
    }

    /**
     * Executa a lógica de filtragem para cada requisição HTTP.
     *
     * <p>Este método extrai o token JWT do cabeçalho da requisição, valida o token e,
     * se válido, recupera os detalhes do usuário e define a autenticação no contexto do Spring Security.</p>
     *
     * @param request     Objeto {@link HttpServletRequest} da requisição HTTP.
     * @param response    Objeto {@link HttpServletResponse} para a resposta HTTP.
     * @param filterChain Objeto {@link FilterChain} para continuar a execução da requisição.
     * @throws ServletException Se ocorrer um erro relacionado ao servlet.
     * @throws IOException      Se ocorrer um erro de entrada/saída.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtStr = getTokenHeader(request);
            if (jwtStr != null && tokenJwt.validacaoJwt(jwtStr)) {
                // Extrai o nome de usuário do token JWT
                String username = tokenJwt.getUsernameJwt(jwtStr);
                // Carrega os detalhes do usuário a partir do nome extraído
                UserDetails userDetails = usuarioDetailsService.loadUserByUsername(username);
                // Cria um objeto de autenticação com as credenciais e permissões do usuário
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // Define os detalhes da autenticação com base na requisição
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Configura a autenticação no contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Não é possível definir a autenticação do usuário: {}", e);
        }

        // Continua o processamento da requisição HTTP
        filterChain.doFilter(request, response);
    }

    /**
     * Obtém o token JWT do cabeçalho da requisição HTTP.
     *
     * <p>Este método verifica se o cabeçalho 'Authorization' contém um token JWT válido
     * e retorna apenas a string do token, sem o prefixo "Bearer ".</p>
     *
     * @param request Objeto {@link HttpServletRequest} da requisição HTTP.
     * @return O token JWT extraído ou {@code null} se não estiver presente.
     */
    private String getTokenHeader(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        // Verifica se o cabeçalho contém um token válido com o prefixo "Bearer "
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
