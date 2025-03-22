package com.sistema.gerenciamento.hospitalar.validates.consultaMedica;

import com.sistema.gerenciamento.hospitalar.dtos.ConsultaRecordDto;

// A interface ValidadorAgendamentoDeConsulta define um contrato para validações relacionadas ao agendamento de consultas.
// Qualquer classe que implementar essa interface deve fornecer uma implementação para o método validar.
public interface ValidadorAgendamentoDeConsulta {

    // Método responsável por validar um agendamento de consulta.
    // Recebe um objeto ConsultaRecordDto contendo os dados da consulta a ser validada.
    // Caso a validação falhe, a implementação pode lançar uma exceção apropriada.
    void validar(ConsultaRecordDto consultaRecordDto);
}
