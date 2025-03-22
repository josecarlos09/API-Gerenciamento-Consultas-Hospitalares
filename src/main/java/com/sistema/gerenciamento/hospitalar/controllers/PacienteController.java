package com.sistema.gerenciamento.hospitalar.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.gerenciamento.hospitalar.dtos.PacienteRecordDto;
import com.sistema.gerenciamento.hospitalar.models.PacienteModel;
import com.sistema.gerenciamento.hospitalar.services.ConsultaService;
import com.sistema.gerenciamento.hospitalar.services.PacienteService;
import com.sistema.gerenciamento.hospitalar.specifications.SpecificationsTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    Logger logger = LogManager.getLogger(PacienteController.class);

    final PacienteService pacienteService;
    final ConsultaService consultaService;

    public PacienteController(PacienteService pacienteService, ConsultaService consultaService) {
        this.pacienteService = pacienteService;
        this.consultaService = consultaService;
    }

    /**
     * Endpoint para cadastrar um novo paciente.
     * Verifica se o paciente já está cadastrado pelo nome, CNS, CPF, RG ou telefone celular.
     * @param pacienteRecordDto DTO contendo os dados do paciente.
     * @return ResponseEntity com status de criação ou conflito em caso de duplicidade.
     */
    @PostMapping
    public ResponseEntity<Object> savePaciente (@RequestBody @Validated(PacienteRecordDto.PacienteView.RegistroPacientePost.class)
                                                @JsonView(PacienteRecordDto.PacienteView.RegistroPacientePost.class)
                                                PacienteRecordDto pacienteRecordDto){
        // Verifica se o paciente já possui cadastro
        if (pacienteService.existByNomeCompleto(pacienteRecordDto.nomeCompleto())){
            logger.warn("ESTE PACIENTE JÁ ESTÁ CADASTRADO");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ESTE PACIENTE JÁ ESTÁ CADASTRADO");
        }
        if (pacienteService.existByCns(pacienteRecordDto.cns())){
            logger.warn("ESTE CNS JÁ ESTÁ CADASTRADO");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ESTE CNS JÁ ESTÁ CADASTRADO");
        }
        if (pacienteService.existByCpf(pacienteRecordDto.cpf())){
            logger.warn("ESTE CPF JÁ ESTÁ VINCULADO A UM PACIENTE");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ESTE CPF JÁ ESTÁ VINCULADO A UM PACIENTE");
        }
        if (pacienteService.existByRg(pacienteRecordDto.rg())){
            logger.warn("ESTE RG JÁ ESTÁ VINCULADO A UM PACIENTE");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ESTE RG JÁ ESTÁ VINCULADO A UM PACIENTE");
        }
        if (pacienteService.existByTelefoneCelular(pacienteRecordDto.telefoneCelular())){
            logger.warn("ESTE TELEFONE CELULAR JÁ ESTÁ SENDO USADO");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ESTE TELEFONE CELULAR JÁ ESTÁ SENDO USADO");
        }
        logger.debug("PACIENTE CADASTRADO COM SUCESSO!");
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.savePaciente(pacienteRecordDto));
    }

    /**
     * Endpoint para buscar todos os pacientes com paginação.
     * @param spec Especificação para filtros dinâmicos.
     * @param pageable Configuração da paginação.
     * @return Página de pacientes.
     */
    @GetMapping
    public ResponseEntity<Page<PacienteModel>> getAllPaciente(@PageableDefault(page = 0, size = 10, sort = "pacienteId", direction = Sort.Direction.ASC)
                                                              SpecificationsTemplate.PacienteSpec spec,
                                                              Pageable pageable){
        Page<PacienteModel> pacienteModel = pacienteService.fidAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteModel);
    }

    /**
     * Endpoint para buscar um paciente específico pelo ID.
     * @param pacienteId ID do paciente.
     * @return Dados do paciente.
     */
    @GetMapping("/{pacienteId}")
    public ResponseEntity<Object> getOnPaciente(@PathVariable(value = "pacienteId")UUID pacienteId){
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.findById(pacienteId));
    }

    /**
     * Endpoint para deletar um paciente.
     * Impede a exclusão se o paciente estiver associado a consultas.
     * @param pacienteId ID do paciente.
     * @return Mensagem de sucesso ou erro.
     */
    @DeleteMapping("/{pacienteId}")
    public ResponseEntity<Object> deletePaciente(@PathVariable(value = "pacienteId")UUID pacienteId){
        if (consultaService.existsByPacienteConsultaPacienteId(pacienteId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Não é possível excluir o paciente, pois ele está associado a uma ou mais consultas.");
        }
        pacienteService.deleteByPacienteId(pacienteService.findById(pacienteId).get());
        return ResponseEntity.status(HttpStatus.OK).body("CADASTRO DE PACIENTE DELETADO COM SUCESSO!");
    }

    /**
     * Endpoint para atualizar os dados de um paciente.
     * @param pacienteId ID do paciente.
     * @param pacienteRecordDto DTO contendo os novos dados do paciente.
     * @return Dados atualizados do paciente.
     */
    @PutMapping("/{pacienteId}/paciente")
    public ResponseEntity<Object> updatePaciente(@PathVariable(value = "pacienteId")UUID pacienteId,
                                                 @RequestBody @Validated(PacienteRecordDto.PacienteView.PacientePut.class)
                                                 @JsonView(PacienteRecordDto.PacienteView.PacientePut.class)
                                                 PacienteRecordDto pacienteRecordDto){
        logger.debug("PUT updatePaciente pacienteId recebido: {} ", pacienteId);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.updatePaciente(pacienteService.findById(pacienteId).get(), pacienteRecordDto));
    }

    /**
     * Endpoint para atualizar o status de um paciente.
     * @param pacienteId ID do paciente.
     * @param pacienteRecordDto DTO contendo o novo status do paciente.
     * @return Status atualizado do paciente.
     */
    @PutMapping("/{pacienteId}/status")
    public ResponseEntity<Object> updateStatus(@PathVariable(value = "pacienteId") UUID pacienteId,
                                               @RequestBody @Validated(PacienteRecordDto.PacienteView.StatusPacientePut.class) PacienteRecordDto pacienteRecordDto){
        logger.debug("PUT: status do usuário atualizado com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.updateStatusPaciente(pacienteService.findById(pacienteId).get(), pacienteRecordDto));
    }
}