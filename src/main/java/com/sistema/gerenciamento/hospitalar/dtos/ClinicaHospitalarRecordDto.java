package com.sistema.gerenciamento.hospitalar.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClinicaHospitalarRecordDto(@NotBlank(groups = ClinicaView.ClinicaPost.class, message = "O campo nome é obrigatorio!")
                                         @JsonView({ClinicaView.ClinicaPost.class, ClinicaView.ClinicaPut.class})
                                         String nome,

                                         @NotBlank(groups = ClinicaView.ClinicaPost.class, message = "O campo CNPJ é obrigatorio!")
                                         @Size(groups = ClinicaView.ClinicaPost.class, min = 14, max = 14, message = "O CNPJ deve conter 14 digitos.")
                                         @JsonView({ClinicaView.ClinicaPost.class, ClinicaView.ClinicaPut.class})
                                         String cnpj,

                                         @NotBlank(groups = ClinicaView.ClinicaPost.class, message = "O campo telefone é obrigatorio!")
                                         @Size(groups = {ClinicaView.ClinicaPost.class, ClinicaView.ClinicaPut.class}, min = 10, max = 10, message = "O telefone celular deve conter 10 digitos.")
                                         @JsonView({ClinicaView.ClinicaPost.class, ClinicaView.ClinicaPut.class})
                                         String telefone,

                                         @NotBlank(groups = ClinicaView.ClinicaPost.class, message = "O campo email é obrigatorio!")
                                         @Email(message = "E-mail invalido!")
                                         @JsonView({ClinicaView.ClinicaPost.class, ClinicaView.ClinicaPut.class})
                                         String email){
    public interface ClinicaView {
        interface ClinicaPost {}
        interface ClinicaPut {}
    }
}