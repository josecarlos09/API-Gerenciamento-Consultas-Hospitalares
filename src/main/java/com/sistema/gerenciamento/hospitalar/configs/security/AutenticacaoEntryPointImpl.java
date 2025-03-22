package com.sistema.gerenciamento.hospitalar.configs.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Implementação personalizada de AuthenticationEntryPoint para lidar com respostas de erro de autenticação.
 *
 * <p>Esta classe intercepta tentativas de acesso não autorizadas e retorna uma resposta HTTP 401 (Unauthorized),
 * fornecendo uma mensagem de erro no corpo da resposta. Além disso, registra o erro no log.</p>
 *
 * <p>Ela é anotada com {@code @Component} para que o Spring gerencie sua instância automaticamente.</p>
 *
 * @author [Seu Nome]
 * @version 1.0
 * @since [Data]
 */
@Component
public class AutenticacaoEntryPointImpl implements AuthenticationEntryPoint {

    Logger logger = LogManager.getLogger(AutenticacaoEntryPointImpl.class);

    /**
     * Manipula tentativas de autenticação falhas.
     *
     * <p>Este método é chamado quando um usuário não autenticado tenta acessar um recurso protegido.
     * Ele registra o erro e responde com status 401 (Unauthorized).</p>
     *
     * @param request       O objeto HttpServletRequest que contém os detalhes da requisição.
     * @param response      O objeto HttpServletResponse usado para enviar a resposta ao cliente.
     * @param authException A exceção gerada ao falhar na autenticação.
     * @throws IOException      Se ocorrer um erro de entrada/saída ao escrever na resposta.
     * @throws ServletException Se ocorrer um erro relacionado ao servlet.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
