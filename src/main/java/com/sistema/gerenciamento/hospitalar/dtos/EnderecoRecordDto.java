package com.sistema.gerenciamento.hospitalar.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;

public record EnderecoRecordDto(@NotBlank(groups = EnderecoView.EnderecoPost.class, message = "O campo LOGRADOURO é obrigatorio!")
                                @JsonView({EnderecoView.EnderecoPost.class, EnderecoView.EnderecoPut.class})
                                String logradouro,

                                @NotBlank(groups = EnderecoView.EnderecoPost.class, message = "O campo CEP é obrigatorio!")
                                @JsonView({EnderecoView.EnderecoPost.class, EnderecoView.EnderecoPut.class})
                                String cep,

                                @NotBlank(groups = EnderecoView.EnderecoPost.class, message = "O campo BAIRRO é obrigatorio!")
                                @JsonView({EnderecoView.EnderecoPost.class, EnderecoView.EnderecoPut.class})
                                String bairro,

                                @NotBlank(groups = EnderecoView.EnderecoPost.class, message = "O campo LOCALIDADE é obrigatorio!")
                                @JsonView({EnderecoView.EnderecoPost.class, EnderecoView.EnderecoPut.class})
                                String localidade,

                                @NotBlank(groups = EnderecoView.EnderecoPost.class, message = "O campo UF é obrigatorio!")
                                @JsonView({EnderecoView.EnderecoPost.class, EnderecoView.EnderecoPut.class})
                                String uf,

                                @JsonView({EnderecoView.EnderecoPost.class, EnderecoView.EnderecoPut.class})
                                String numeroCasa,

                                @JsonView({EnderecoView.EnderecoPost.class, EnderecoView.EnderecoPut.class})
                                String complemento,

                                @JsonView({EnderecoView.EnderecoPost.class, EnderecoView.EnderecoPut.class})
                                String ibge){
    public interface EnderecoView {
        interface EnderecoPost {}
        interface EnderecoPut {}
    }
}
