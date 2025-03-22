package com.sistema.gerenciamento.hospitalar.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.gerenciamento.hospitalar.enums.StatusAtendimento;
import com.sistema.gerenciamento.hospitalar.enums.TipoAtendimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaRecordDto(@NotNull(groups = ConsultaView.ConsultaPost.class, message = "O campo data é obrigatorio!")
                                @JsonView({ConsultaView.ConsultaPost.class, ConsultaView.ConsultaPut.class})
                                LocalDateTime dataConsulta,

                                @NotNull(groups = ConsultaView.ConsultaPost.class, message = "O campo nome tipo atendimento é obrigatorio!")
                                @JsonView({ConsultaView.ConsultaPost.class, ConsultaView.ConsultaPut.class})
                                TipoAtendimento tipoAtendimento,

                                @NotNull(groups = ConsultaView.ConsultaPost.class, message = "O campo nome tipo atendimento é obrigatorio!")
                                @JsonView({ConsultaView.ConsultaPost.class, ConsultaView.ConsultaPut.class})
                                float valorConsulta,

                                @NotBlank(groups = ConsultaView.ConsultaPost.class, message = "O campo local da consulta é obrigatorio!")
                                @JsonView({ConsultaView.ConsultaPost.class, ConsultaView.ConsultaPut.class})
                                String local,

                                @NotNull(groups = ConsultaView.finalizarConsulta.class, message = "Informe o status da consulta!")
                                @JsonView(ConsultaView.finalizarConsulta.class)
                                StatusAtendimento statusAtendimento,

                                @JsonView(ConsultaView.finalizarConsulta.class)
                                String observacao,

                                @JsonView(ConsultaView.finalizarConsulta.class)
                                String resultadoConsulta){
    public interface ConsultaView {
        interface ConsultaPost {}
        interface ConsultaPut {}
        interface finalizarConsulta{}
    }
}