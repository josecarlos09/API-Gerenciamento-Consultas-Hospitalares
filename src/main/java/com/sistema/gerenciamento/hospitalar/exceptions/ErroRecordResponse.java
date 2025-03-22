package com.sistema.gerenciamento.hospitalar.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * Este record é utilizado para encapsular informações sobre um erro ocorrido
 * na execução de uma operação, como código do erro, mensagem do erro e detalhes adicionais.
 *
 * A anotação `@JsonInclude(JsonInclude.Include.NON_NULL)` é usada para garantir que campos com valores nulos
 * não sejam incluídos na resposta JSON, mantendo a resposta mais limpa.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroRecordResponse(int codigoErro,
                                 String mensagemErro,
                                 Map<String, String> detalhesErro
){}
