package com.sistema.gerenciamento.hospitalar.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por centralizar o tratamento de exceções globais na aplicação.
 * Esta classe utiliza o `@ControllerAdvice` para capturar exceções lançadas durante
 * a execução da aplicação e retorna uma resposta adequada ao cliente.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Logger para registrar mensagens de erro e advertência.
    Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Trata exceções do tipo NotFoundException.
     * Retorna uma resposta com o código de erro 404 (NOT_FOUND) e a mensagem da exceção.
     *
     * @param exception A exceção capturada.
     * @return ResponseEntity com o código de erro e a mensagem da exceção.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroRecordResponse> handleNotFoundException(NotFoundException exception){
        // Usa o DTO ErroRecordResponse para encapsular a resposta de erro
        var erroRecordResponse = new ErroRecordResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                null // Não há detalhes adicionais neste caso
        );

        // Registra o erro no log
        logger.error("ERRO: erroRecordResponse");

        // Retorna a resposta com o status HTTP 404 (Not Found) e o corpo com a resposta de erro
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroRecordResponse);
    }

    /**
     * Trata exceções de validação de dados nos DTOs.
     * Retorna uma resposta com o código de erro 400 (BAD_REQUEST), uma mensagem
     * genérica de erro de validação e os erros detalhados dos campos.
     *
     * @param ex A exceção de validação capturada.
     * @return ResponseEntity com o código de erro e os detalhes dos erros de validação.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRecordResponse> handleValidationException(MethodArgumentNotValidException ex){
        // Mapa para armazenar os erros de validação dos campos
        Map<String, String> erros = new HashMap<>();

        // Itera sobre todos os erros de validação e os adiciona ao mapa
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String nomeCampo = ((FieldError) error).getField(); // Nome do campo com erro
            String mensagemErro = error.getDefaultMessage(); // Mensagem de erro
            erros.put(nomeCampo, mensagemErro); // Adiciona ao mapa de erros
        });

        // Cria a resposta de erro utilizando o DTO ErroRecordResponse
        var erroRecordResponse = new ErroRecordResponse(
                HttpStatus.BAD_REQUEST.value(), // Código de erro 400
                "ERRO DE VALIDAÇÃO", // Mensagem genérica
                erros // Detalhes dos erros de validação
        );

        // Registra o erro de validação no log
        logger.error("ERRO DE VALIDAÇÃO");

        // Retorna a resposta com o status HTTP 400 (Bad Request) e o corpo com os detalhes dos erros
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroRecordResponse);
    }

    /**
     * Trata exceções de requisições mal formatadas ou com corpo ausente.
     * Retorna uma resposta com o código de erro 400 (BAD_REQUEST) e uma mensagem
     * indicando que o corpo da requisição está mal formatado ou ausente.
     *
     * @param ex A exceção de mensagem não legível capturada.
     * @return ResponseEntity com o código de erro e a mensagem explicativa.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        // Registra o aviso no log sobre corpo ausente ou mal formatado
        logger.warn("Erro: Corpo da requisição ausente ou mal formatado.");

        // Retorna a resposta com o status HTTP 400 (Bad Request) e a mensagem explicativa
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: O corpo da requisição está ausente ou mal formatado.");
    }
}