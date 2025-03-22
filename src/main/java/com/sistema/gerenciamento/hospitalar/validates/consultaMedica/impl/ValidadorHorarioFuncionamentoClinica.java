package com.sistema.gerenciamento.hospitalar.validates.consultaMedica.impl;

import com.sistema.gerenciamento.hospitalar.dtos.ConsultaRecordDto;
import com.sistema.gerenciamento.hospitalar.exceptions.NotFoundException;
import com.sistema.gerenciamento.hospitalar.validates.consultaMedica.ValidadorAgendamentoDeConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
/* A classe ValidadorHorarioFuncionamentoClinica implementa a interface ValidadorAgendamentoDeConsulta
 * e tem o objetivo de garantir que as consultas sejam agendadas dentro do horário de funcionamento da clínica.
*/
@Component() // A anotação @Component torna a classe um componente Spring, permitindo que o Spring gerencie a instância desta classe automaticamente.
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    // O método validar é responsável por verificar se o horário da consulta está dentro do período de funcionamento da clínica.
    @Override
    public void validar(ConsultaRecordDto consultaRecordDto) {
        // Obtém a data e hora da consulta a partir do DTO (Data Transfer Object).
        var dataConsulta = consultaRecordDto.dataConsulta();

        // Verifica se a consulta está marcada para um domingo (day of week igual a SUNDAY).
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        // Verifica se a consulta está marcada para antes da abertura da clínica (antes das 7h).
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;

        // Verifica se a consulta está marcada para depois do horário de encerramento da clínica (depois das 18h).
        var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;

        // Se a consulta for agendada para um domingo ou fora do horário de funcionamento (antes das 7h ou depois das 18h),
        // uma exceção personalizada NotFoundException é lançada com a mensagem de erro.
        if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
            throw new NotFoundException("Horario fora do funcionamento da clinica");
        }
    }
}
