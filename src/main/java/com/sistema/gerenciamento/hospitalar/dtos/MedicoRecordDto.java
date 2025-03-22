package com.sistema.gerenciamento.hospitalar.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.gerenciamento.hospitalar.enums.Especialidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicoRecordDto(@NotBlank(groups = MedicoView.MedicoPost.class, message = "O campo CRM é obrigatorio!")
                              @Size(groups = MedicoView.MedicoPost.class, min = 8, max = 8, message = "O CRM deve conter 8 caracteres")
                              @JsonView(MedicoView.MedicoPost.class)
                              String crm,

                              @NotBlank(groups = MedicoView.MedicoPost.class, message = "O campo CNS é obrigatorio!")
                              @Size(groups = MedicoView.MedicoPost.class, min = 10, max = 10, message = "O CNS deve conter 10 caracteres")
                              @JsonView(MedicoView.MedicoPost.class)
                              String cns,

                              @NotNull(groups = MedicoView.MedicoPost.class, message = "Indique a especialidade do médico!")
                              @JsonView({MedicoView.MedicoPost.class, MedicoView.MedicoPut.class})
                              Especialidade especialidade){
    public interface MedicoView {
        interface MedicoPost {}
        interface MedicoPut {}
    }
}