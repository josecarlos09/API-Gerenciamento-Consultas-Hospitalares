package com.sistema.gerenciamento.hospitalar.validates;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

// Indica que essa anotação fará parte da documentação do JavaDoc.
@Documented

// Define a classe que implementará a lógica de validação da senha (SenhaConstraintImpl).
@Constraint(validatedBy = SenhaConstraintImpl.class)

// Define os locais onde a anotação pode ser aplicada: em métodos e campos de classe.
@Target({ElementType.METHOD, ElementType.FIELD})

// Indica que a anotação será avaliada em tempo de execução.
@Retention(RetentionPolicy.RUNTIME)
public @interface SenhaConstraint {

    /**
     * Mensagem padrão de erro que será retornada caso a senha não atenda aos requisitos definidos.
     */
    String message() default """
            A senha deve conter entre 5 e 20 caracteres,
            a senha deve incluir pelo menos uma letra maiúscula (A-Z),
            a senha deve incluir pelo menos uma letra minúscula (a-z),
            a senha deve conter pelo menos um número (0-9),
            a senha deve incluir pelo menos um caractere especial dentre os seguintes: @, $, !, %, *, ?, &.
            """;

    /**
     * Permite a definição de grupos de validação, que podem ser usados para aplicar regras de validação
     * em diferentes contextos dentro da aplicação.
     */
    Class<?>[] groups() default {};

    /**
     * Permite adicionar informações adicionais sobre a falha de validação.
     * Normalmente, é usado para integração com outras bibliotecas de validação.
     */
    Class<? extends Payload>[] payload() default {};
}