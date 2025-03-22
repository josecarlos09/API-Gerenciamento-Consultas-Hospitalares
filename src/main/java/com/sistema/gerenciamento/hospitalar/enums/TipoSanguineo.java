package com.sistema.gerenciamento.hospitalar.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum que representa os tipos sanguíneos possíveis.
 * Cada valor do enum é associado a uma descrição que corresponde ao tipo sanguíneo
 * em formato de string, como "A+", "O-", etc.
 *
 * O enum utiliza anotações para personalizar a serialização e desserialização de valores
 * quando interage com JSON.
 */
public enum TipoSanguineo {
    A_POSITIVO("A+"),
    A_NEGATIVO("A-"),
    B_POSITIVO("B+"),
    B_NEGATIVO("B-"),
    AB_POSITIVO("AB+"),
    AB_NEGATIVO("AB-"),
    O_POSITIVO("O+"),
    O_NEGATIVO("O-");

    //Variavel de descrição
    private final String descricao;

    /**
     * Construtor para associar a descrição de cada tipo sanguíneo.
     *
     * @param descricao a descrição do tipo sanguíneo (como "A+" ou "O-").
     */
    TipoSanguineo(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue // Define que o JSON usará essa string ao serializar o enum
    public String getDescricao() {
        return descricao;
    }

    /**
     * Método responsável por desserializar uma string para o valor correspondente do enum.
     * A anotação `@JsonCreator` instrui o Jackson a utilizar esse método ao desserializar uma string para o enum.
     *
     * @param descricao a string a ser convertida para o enum.
     * @return o tipo sanguíneo correspondente à string fornecida.
     * @throws IllegalArgumentException caso a string não corresponda a nenhum tipo sanguíneo válido.
     */
    @JsonCreator // Permite desserializar um valor string para um enum
    public static TipoSanguineo fromDescricao(String descricao) {
        for (TipoSanguineo tipo : TipoSanguineo.values()) {
            if (tipo.descricao.equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo sanguíneo inválido: " + descricao);
    }
}