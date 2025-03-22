package com.sistema.gerenciamento.hospitalar.exceptions;

/**
 * Exceção personalizada que representa o erro de "Recurso Não Encontrado".
 * Esta exceção é lançada quando um recurso solicitado não é encontrado na aplicação,
 * como ao tentar acessar um dado que não existe ou foi removido.
 * Extende a classe RuntimeException para ser uma exceção não verificada.
 */
public class NotFoundException extends RuntimeException{
    /**
     * Construtor da exceção NotFoundException.
     *
     * @param message A mensagem de erro que será associada à exceção.
     */    public NotFoundException(String message) {
        super(message);
    }
}