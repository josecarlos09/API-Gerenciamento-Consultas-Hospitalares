package com.sistema.gerenciamento.hospitalar.configs.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    /**
     * Método chamado automaticamente quando um acesso é negado.
     *
     * @param request O objeto HttpServletRequest da requisição.
     * @param response O objeto HttpServletResponse da resposta.
     * @param accessDeniedException A exceção que representa o acesso negado.
     * @throws IOException Se ocorrer um erro de entrada/saída.
     * @throws ServletException Se ocorrer um erro na manipulação do servlet.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value()); // Define o status HTTP 403 (Proibido)
    }
}