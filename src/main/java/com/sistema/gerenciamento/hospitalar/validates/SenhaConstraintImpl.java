package com.sistema.gerenciamento.hospitalar.validates;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Implementação da validação de senha baseada na anotação {@link SenhaConstraint}.
 * Esta classe verifica se a senha atende aos critérios definidos pelo padrão de regex.
 */
public class SenhaConstraintImpl implements ConstraintValidator<SenhaConstraint, String> {

    // Expressão regular para validar a senha.
    // Requisitos:
    // - Pelo menos um número (0-9)
    // - Pelo menos uma letra minúscula (a-z)
    // - Pelo menos uma letra maiúscula (A-Z)
    // - Pelo menos um caractere especial (!@#&()–[{}]:;',?/*~$^+=<>)
    // - Entre 5 e 20 caracteres de comprimento
    private static final String SENHA_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{5,20}$";

    /**
     * Método de inicialização da validação.
     * Não é necessário implementar lógica específica aqui para essa validação.
     */
    @Override
    public void initialize(SenhaConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Método responsável por validar a senha conforme os critérios estabelecidos.
     *
     * @param senha   A senha fornecida para validação.
     * @param context Contexto da validação.
     * @return {@code true} se a senha for válida, {@code false} caso contrário.
     */
    @Override
    public boolean isValid(String senha, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(SENHA_PATTERN); // Compila a expressão regular para validação

        // Verifica se a senha é nula, vazia ou contém espaços em branco
        if (senha == null || senha.trim().isEmpty() || senha.contains(" ")) {
            return false;
        }

        // Verifica se a senha corresponde ao padrão definido
        return pattern.matcher(senha).matches();
    }
}