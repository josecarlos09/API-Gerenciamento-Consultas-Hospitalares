package com.sistema.gerenciamento.hospitalar.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.gerenciamento.hospitalar.enums.Funcao;
import com.sistema.gerenciamento.hospitalar.enums.Genero;
import com.sistema.gerenciamento.hospitalar.enums.StatusFuncionario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record FuncionarioRecordDto(@NotBlank(groups = FuncionarioView.RegistroFuncionarioPost.class, message = "O campo nome completo é obrigatorio!")
                                   @Size(groups = FuncionarioView.RegistroFuncionarioPost.class, min = 5, max = 50, message = "O número máximo de caracteres é 50, e o mínimo é 5.")
                                   @JsonView(FuncionarioView.RegistroFuncionarioPost.class)
                                   String nomeCompleto,

                                   @NotBlank(groups = FuncionarioView.RegistroFuncionarioPost.class, message = "O campo CPF é obrigatorio!")
                                   @Size(groups = FuncionarioView.RegistroFuncionarioPost.class, min = 11, max = 50, message = "O número de caracteres é 11.")
                                   @JsonView(FuncionarioView.RegistroFuncionarioPost.class)
                                   String cpf,

                                   @NotNull(groups = FuncionarioView.RegistroFuncionarioPost.class, message = "O campo RG é obrigatorio!")
                                   @Size(groups = FuncionarioView.RegistroFuncionarioPost.class, min = 9, max = 11, message = "Informe o número de caracteres do RG.")
                                   @JsonView(FuncionarioView.RegistroFuncionarioPost.class)
                                   String rg,

                                   @NotNull(groups = FuncionarioView.RegistroFuncionarioPost.class, message = "O campo GÊNERO é obrigatorio!")
                                   @JsonView(FuncionarioView.RegistroFuncionarioPost.class)
                                   Genero genero,

                                   @NotNull(groups = FuncionarioView.RegistroFuncionarioPost.class, message = "O campo DATA DE NASCIMENTO é obrigatorio!")
                                   @JsonView(FuncionarioView.RegistroFuncionarioPost.class)
                                   LocalDate dataNascimento,

                                   @JsonView({FuncionarioView.RegistroFuncionarioPost.class, FuncionarioView.FuncionarioPut.class})
                                   String telefone,

                                   @NotNull(groups = {FuncionarioView.RegistroFuncionarioPost.class}, message = "O campo DATA DE NASCIMENTO é obrigatorio!")
                                   @JsonView({FuncionarioView.RegistroFuncionarioPost.class, FuncionarioView.FuncionarioPut.class})
                                   Funcao funcao,

                                   @JsonView(FuncionarioView.StatusFuncionarioPut.class)
                                   StatusFuncionario statusFuncionario
){
    public interface FuncionarioView {
        interface RegistroFuncionarioPost {}
        interface FuncionarioPut {}
        interface StatusFuncionarioPut {}
    }
}
