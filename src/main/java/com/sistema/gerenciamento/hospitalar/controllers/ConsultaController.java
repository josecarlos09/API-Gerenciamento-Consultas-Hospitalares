package com.sistema.gerenciamento.hospitalar.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.gerenciamento.hospitalar.dtos.ConsultaRecordDto;
import com.sistema.gerenciamento.hospitalar.models.ConsultaModel;
import com.sistema.gerenciamento.hospitalar.services.ClinicaHospitalarService;
import com.sistema.gerenciamento.hospitalar.services.ConsultaService;
import com.sistema.gerenciamento.hospitalar.services.MedicoService;
import com.sistema.gerenciamento.hospitalar.services.PacienteService;
import com.sistema.gerenciamento.hospitalar.specifications.SpecificationsTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador para gerenciar operações relacionadas às consultas médicas.
 */
@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    Logger logger = LogManager.getLogger(ConsultaController.class);

    private final ConsultaService consultaService;
    private final MedicoService medicoService;
    private final PacienteService pacienteService;
    private final ClinicaHospitalarService clinicaHospitalarService;

    public ConsultaController(ConsultaService consultaService, MedicoService medicoService, PacienteService pacienteService, ClinicaHospitalarService clinicaHospitalarService) {
        this.consultaService = consultaService;
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
        this.clinicaHospitalarService = clinicaHospitalarService;
    }

    /**
     * Registra uma nova consulta médica.
     * @param consultaRecordDto Dados da consulta.
     * @param medicoId ID do médico responsável.
     * @param pacienteId ID do paciente atendido.
     * @param clinicaId ID da clínica onde ocorrerá a consulta.
     * @return ResponseEntity contendo a consulta registrada.
     */
    @PostMapping("medico/{medicoId}/paciente/{pacienteId}/clinica/{clinicaId}")
    public ResponseEntity<Object> registroConsulta(@RequestBody @Validated(ConsultaRecordDto.ConsultaView.ConsultaPost.class)
                                                   @JsonView(ConsultaRecordDto.ConsultaView.ConsultaPost.class)
                                                   ConsultaRecordDto consultaRecordDto,
                                                   @PathVariable(value = "medicoId") UUID medicoId,
                                                   @PathVariable(value = "pacienteId") UUID pacienteId,
                                                   @PathVariable(value = "clinicaId") UUID clinicaId){
        logger.debug("POST: Registro de consulta {}", consultaRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consultaService.saveConsulta(consultaRecordDto,
                        medicoService.findById(medicoId).get(),
                        pacienteService.findById(pacienteId).get(),
                        clinicaHospitalarService.findById(clinicaId).get()));
    }

    /**
     * Obtém todas as consultas médicas com filtros opcionais.
     * @param spec Especificações para filtragem.
     * @param pageable Paginação.
     * @return Lista paginada de consultas médicas.
     */
    @GetMapping
    public ResponseEntity<Page<ConsultaModel>> getAllConsulta(SpecificationsTemplate.ConsultaSpec spec,
                                                              Pageable pageable){
        Page<ConsultaModel> consultaModel = consultaService.fidAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(consultaModel);
    }

    /**
     * Obtém uma consulta médica pelo ID.
     * @param consultaId ID da consulta.
     * @return Consulta médica correspondente ao ID fornecido.
     */
    @GetMapping("/{consultaId}")
    public ResponseEntity<Object> getOnConsultaMedica(@PathVariable(value = "consultaId") UUID consultaId){
        logger.debug("GET ONE: Consulta médica!");
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.findById(consultaId));
    }

    /**
     * Finaliza uma consulta médica, alterando seu status para "Atendido".
     * @param consultaId ID da consulta a ser finalizada.
     * @param consultaRecordDto Dados da consulta atualizados.
     * @return Consulta finalizada.
     */
    @PutMapping("/{consultaId}/atendido")
    public ResponseEntity<Object> finalizarConsultaMedica(@PathVariable(value = "consultaId")UUID consultaId,
                                                          @RequestBody @Validated(ConsultaRecordDto.ConsultaView.finalizarConsulta.class)
                                                          @JsonView(ConsultaRecordDto.ConsultaView.finalizarConsulta.class)
                                                          ConsultaRecordDto consultaRecordDto){
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.finalizarConsultaMedica(consultaRecordDto, consultaService.findById(consultaId).get()));
    }

    /**
     * Atualiza os dados de uma consulta médica existente.
     * @param consultaId ID da consulta a ser atualizada.
     * @param consultaRecordDto Novos dados da consulta.
     * @return Consulta atualizada.
     */
    @PutMapping("/{consultaId}/consulta")
    public ResponseEntity<Object> atualizarConsultaMedica(@PathVariable(value = "consultaId")UUID consultaId,
                                                          @RequestBody @Validated(ConsultaRecordDto.ConsultaView.ConsultaPut.class)
                                                          @JsonView(ConsultaRecordDto.ConsultaView.ConsultaPut.class)
                                                          ConsultaRecordDto consultaRecordDto){
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.atualizarConsultaMedica(consultaRecordDto, consultaService.findById(consultaId).get()));
    }

    /**
     * Exclui uma consulta médica pelo ID.
     * @param consultaId ID da consulta a ser excluída.
     * @return Mensagem de sucesso.
     */
    @DeleteMapping("/{consultaId}")
    public ResponseEntity<Object> deleteConsultaMedica(@PathVariable(value = "consultaId") UUID consultaId) {
        consultaService.deleteByConsultaId(consultaService.findById(consultaId).get());
        return ResponseEntity.status(HttpStatus.OK).body("CONSULTA MÉDICA DELETADA COM SUCESSO!");
    }
}