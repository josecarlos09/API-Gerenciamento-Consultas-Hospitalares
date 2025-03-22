package com.sistema.gerenciamento.hospitalar.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.gerenciamento.hospitalar.dtos.ClinicaHospitalarRecordDto;
import com.sistema.gerenciamento.hospitalar.models.ClinicaHospitalarModel;
import com.sistema.gerenciamento.hospitalar.services.ClinicaHospitalarService;
import com.sistema.gerenciamento.hospitalar.services.ConsultaService;
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
 * Controlador REST responsável pelo gerenciamento das clínicas hospitalares.
 */
@RestController
@RequestMapping("/clinica")
public class ClinicaHospitalarController {

    private static final Logger logger = LogManager.getLogger(ClinicaHospitalarController.class);

    private final ClinicaHospitalarService clinicaHospitalarService;
    private final ConsultaService consultaService;

    /**
     * Construtor da classe, inicializa os serviços necessários.
     */
    public ClinicaHospitalarController(ClinicaHospitalarService clinicaHospitalarService, ConsultaService consultaService) {
        this.clinicaHospitalarService = clinicaHospitalarService;
        this.consultaService = consultaService;
    }

    /**
     * Endpoint para cadastrar uma nova clínica hospitalar.
     */
    @PostMapping
    public ResponseEntity<Object> saveClinicaHospitalar(@RequestBody @Validated(ClinicaHospitalarRecordDto.ClinicaView.ClinicaPost.class)
                                                        @JsonView(ClinicaHospitalarRecordDto.ClinicaView.ClinicaPost.class)
                                                        ClinicaHospitalarRecordDto clinicaHospitalarRecordDto){

        // Verifica se já existe uma clínica com o mesmo nome
        if (clinicaHospitalarService.existByNome(clinicaHospitalarRecordDto.nome())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, Esse nome já existe!");
        }
        // Verifica se o CNPJ já está cadastrado
        if (clinicaHospitalarService.existByCnpj(clinicaHospitalarRecordDto.cnpj())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, Esse CNPJ já está em uso!");
        }
        // Verifica se o email já está cadastrado
        if (clinicaHospitalarService.existByEmail(clinicaHospitalarRecordDto.email())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, Esse E-mail já está em uso!");
        }

        logger.debug("POST: Registro de clínica {}", clinicaHospitalarRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clinicaHospitalarService.saveClinicaHospitalar(clinicaHospitalarRecordDto));
    }

    /**
     * Endpoint para listar todas as clínicas hospitalares com suporte a paginação e filtragem.
     */
    @GetMapping
    public ResponseEntity<Page<ClinicaHospitalarModel>> getAllClinica(SpecificationsTemplate.ClinicaSpec spec,
                                                                      Pageable pageable){
        Page<ClinicaHospitalarModel> clinicaHospitalarModels = clinicaHospitalarService.fidAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(clinicaHospitalarModels);
    }

    /**
     * Endpoint para buscar uma clínica hospitalar específica pelo ID.
     */
    @GetMapping("/{clinicaId}")
    public ResponseEntity<Object> getOnClinicaHospitalar(@PathVariable(value = "clinicaId") UUID clinicaId){
        logger.debug("GET ONE: Clínica hospitalar!");
        return ResponseEntity.status(HttpStatus.OK).body(clinicaHospitalarService.findById(clinicaId));
    }

    /**
     * Endpoint para atualizar os dados de uma clínica hospitalar existente.
     */
    @PutMapping("/{clinicaId}")
    public ResponseEntity<Object> atualizarClinica(@PathVariable(value = "clinicaId")UUID clinicaId,
                                                   @RequestBody @Validated(ClinicaHospitalarRecordDto.ClinicaView.ClinicaPut.class)
                                                   @JsonView(ClinicaHospitalarRecordDto.ClinicaView.ClinicaPut.class)
                                                   ClinicaHospitalarRecordDto clinicaHospitalarRecordDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(clinicaHospitalarService.atualizarClinicaHospitalar(clinicaHospitalarRecordDto, clinicaHospitalarService.findById(clinicaId).get()));
    }

    /**
     * Endpoint para excluir uma clínica hospitalar pelo ID.
     * Antes da exclusão, verifica se há consultas associadas à clínica.
     */
    @DeleteMapping("/{clinicaId}")
    public ResponseEntity<Object> deleteClinicaHospitalar(@PathVariable(value = "clinicaId") UUID clinicaId){

        // Verifica se há consultas associadas à clínica antes da exclusão
        if (consultaService.existsByClinicaConsultaClinicaHospitalarId(clinicaId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Não é possível excluir os dados da clínica, pois ela está associada a uma ou mais consultas.");
        }

        clinicaHospitalarService.deleteByClinicaId(clinicaHospitalarService.findById(clinicaId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Clínica médica deletada com sucesso!");
    }
}