package com.sistema.gerenciamento.hospitalar.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RaquestLoggingFiltroConfig {

    // Configuração de logs
    @Bean
    public CommonsRequestLoggingFilter loggingFilter(){
        var filter = new CommonsRequestLoggingFilter();

        filter.setIncludeQueryString(true); // Incluindo a query String
        filter.setIncludePayload(true); // Payload da mensagem
        filter.setMaxPayloadLength(10000); // limite do payload
        filter.setAfterMessagePrefix("SOLICITAÇÃO DE DADOS: "); // Mensagem fixa
        filter.setHeaderPredicate(header -> !header.equalsIgnoreCase("authorization")); // Ingnore a tag authorization

        return filter;
    }
}