package com.sistema.gerenciamento.hospitalar.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.gerenciamento.hospitalar.enums.Genero;
import com.sistema.gerenciamento.hospitalar.enums.StatusPaciente;
import com.sistema.gerenciamento.hospitalar.enums.TipoSanguineo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PacienteRecordDto(@NotBlank(groups = PacienteView.RegistroPacientePost.class, message = "O campo nome é obrigatorio para o cadastro do paciente!")
                                @Size(groups = PacienteView.RegistroPacientePost.class, min = 5, max = 50, message = "O número máximo de caracteres é 50, e o mínimo é 5.")
                                @JsonView(PacienteView.RegistroPacientePost.class)
                                String nomeCompleto,

                                @NotNull(groups = PacienteView.RegistroPacientePost.class, message = "O campo CPF é obrigatorio!")
                                @Size(groups = PacienteView.RegistroPacientePost.class, min = 11, max = 11, message = "O número de CPF deve conter 11 caracteres")
                                @JsonView(PacienteView.RegistroPacientePost.class)
                                String cpf,

                                @NotNull(groups = PacienteView.RegistroPacientePost.class, message = "O campo RG é obrigatorio!")
                                @Size(groups = PacienteView.RegistroPacientePost.class, min = 9, max = 11, message = "O número de RG deve conter de 9 a 11 caracteres")
                                @JsonView(PacienteView.RegistroPacientePost.class)
                                String rg,

                                @JsonView(PacienteView.StatusPacientePut.class)
                                StatusPaciente statusPaciente,

                                @JsonView({PacienteView.RegistroPacientePost.class, PacienteView.PacientePut.class})
                                String telefoneCelular,

                                @NotNull(groups = PacienteView.RegistroPacientePost.class, message = "O campo DATA DE NASCIMENTO é obrigatorio!")
                                @JsonView(PacienteView.RegistroPacientePost.class)
                                LocalDate dataNascimento,

                                @NotNull(groups = PacienteView.RegistroPacientePost.class, message = "O campo GENERO é obrigatório!")
                                @JsonView(PacienteView.RegistroPacientePost.class)
                                Genero genero,

                                @NotNull(groups = PacienteView.RegistroPacientePost.class, message = "O campo CNS é obrigatório!")
                                @Size(groups = PacienteView.RegistroPacientePost.class, min = 12, max = 12, message = "O número de CNS deve conter 12 caracteres")
                                @JsonView(PacienteView.RegistroPacientePost.class)
                                String cns,

                                @NotNull(groups = PacienteView.RegistroPacientePost.class, message = "O campo TIPO SANGUINEO é obrigatório")
                                @JsonView({PacienteView.RegistroPacientePost.class, PacienteView.PacientePut.class})
                                TipoSanguineo tipoSanguineo,

                                @NotNull(groups = PacienteView.RegistroPacientePost.class, message = "O campo NACIONALIDADE é obrigatório!")
                                @Size(groups = {PacienteView.RegistroPacientePost.class, PacienteView.PacientePut.class}, min = 5, max = 30, message = "A nacionalidade deve conter no minimo 5 e no maximo 50 caracteres")
                                @JsonView({PacienteView.RegistroPacientePost.class, PacienteView.PacientePut.class})
                                String nacionalidade){
    public interface PacienteView {
        interface RegistroPacientePost {}
        interface PacientePut {}
        interface StatusPacientePut {}
    }
}