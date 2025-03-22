package com.sistema.gerenciamento.hospitalar.validates.consultaMedica.impl;

import com.sistema.gerenciamento.hospitalar.dtos.ConsultaRecordDto;
import com.sistema.gerenciamento.hospitalar.exceptions.NotFoundException;
import com.sistema.gerenciamento.hospitalar.validates.consultaMedica.ValidadorAgendamentoDeConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

/* A classe ValidadorHorarioAntecedencia é um validador que implementa a interface ValidadorAgendamentoDeConsulta
 * com o objetivo de garantir que uma consulta seja agendada com pelo menos 30 minutos de antecedência.
 */
@Component() // A anotação @Component torna a classe um componente Spring, permitindo que o Spring gerencie a instância desta classe automaticamente.
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {

    // O método validar é responsável por realizar a validação do agendamento da consulta.
    // Ele verifica se a consulta foi marcada com pelo menos 30 minutos de antecedência.
    @Override
    public void validar(ConsultaRecordDto consultaRecordDto) {
        // Obtém a data e hora da consulta a partir do DTO (Data Transfer Object).
        var dataConsulta = consultaRecordDto.dataConsulta();

        // Obtém o horário atual considerando o fuso horário de Recife (America/Recife).
        var agora = LocalDateTime.now(ZoneId.of("America/Recife"));

        // Calcula a diferença entre o horário atual e o horário da consulta em minutos.
        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        // Verifica se a diferença de tempo é inferior a 30 minutos.
        if (diferencaEmMinutos < 30) {
            // Se a consulta foi marcada com menos de 30 minutos de antecedência,
            // uma exceção personalizada NotFoundException é lançada com a mensagem de erro.
            throw new NotFoundException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}