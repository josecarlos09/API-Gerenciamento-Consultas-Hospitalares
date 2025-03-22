package com.sistema.gerenciamento.hospitalar.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.gerenciamento.hospitalar.dtos.MedicoRecordDto;
import com.sistema.gerenciamento.hospitalar.models.MedicoModel;
import com.sistema.gerenciamento.hospitalar.services.ConsultaService;
import com.sistema.gerenciamento.hospitalar.services.FuncionarioService;
import com.sistema.gerenciamento.hospitalar.services.MedicoService;
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
 * Controlador REST para gerenciar médicos dentro do sistema hospitalar.
 * Permite registrar, atualizar, excluir e consultar médicos.
 */
@RestController
@RequestMapping("/medico")
public class MedicoController {

    // Logger para registrar eventos e ações no sistema
    Logger logger = LogManager.getLogger(MedicoController.class);

    // Serviços necessários para operações relacionadas a médicos e funcionários
    final MedicoService medicoService;
    final FuncionarioService funcionarioService;
    final ConsultaService consultaService;

    /**
     * Construtor para injeção de dependências.
     */
    public MedicoController(MedicoService medicoService, FuncionarioService funcionarioService, ConsultaService consultaService) {
        this.medicoService = medicoService;
        this.funcionarioService = funcionarioService;
        this.consultaService = consultaService;
    }

    /**
     * Registra um novo médico associado a um funcionário.
     *
     * @param medicoRecordDto DTO contendo os dados do médico a ser cadastrado.
     * @param funcionarioId ID do funcionário ao qual o médico será vinculado.
     * @return ResponseEntity com status e mensagem de resposta.
     */
    @PostMapping("/registro/{funcionarioId}/funcionario")
    public ResponseEntity<Object> registroMedico(@RequestBody @Validated(MedicoRecordDto.MedicoView.MedicoPost.class)
                                                 @JsonView(MedicoRecordDto.MedicoView.MedicoPost.class)
                                                 MedicoRecordDto medicoRecordDto,
                                                 @PathVariable(value = "funcionarioId") UUID funcionarioId) {
        // Validações para evitar cadastros duplicados
        if (medicoService.existByCrm(medicoRecordDto.crm())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, CRM JÁ EXISTENTE!");
        }
        if (medicoService.existByCns(medicoRecordDto.cns())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, ESSE CNS JÁ ESTÁ CADASTRADO!");
        }
        if (medicoService.existsByFuncionarioMedicoFuncionarioId(funcionarioId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, ESSE MÉDICO JÁ ESTÁ ASSOCIADO A UM USUÁRIO!");
        }

        logger.debug("POST: Cadastro de médico {}", medicoRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.saveMedico(medicoRecordDto, funcionarioService.findById(funcionarioId).get()));
    }

    /**
     * Obtém os detalhes de um médico específico.
     *
     * @param medicoId ID do médico a ser consultado.
     * @return ResponseEntity contendo os detalhes do médico.
     */
    @GetMapping("/{medicoId}")
    public ResponseEntity<Object> getOnMedico(@PathVariable(value = "medicoId") UUID medicoId) {
        logger.debug("GET: getOnMedico, consulta: {}", medicoId);
        return ResponseEntity.status(HttpStatus.OK).body(medicoService.findById(medicoId));
    }

    /**
     * Obtém uma lista paginada de todos os médicos cadastrados.
     *
     * @param spec Especificação para filtragem.
     * @param pageable Paginação dos resultados.
     * @return Página contendo a lista de médicos.
     */
    @GetMapping
    public ResponseEntity<Page<MedicoModel>> getAllMedico(SpecificationsTemplate.MedicoSpec spec,
                                                          Pageable pageable) {
        Page<MedicoModel> medicoModelPage = medicoService.fidAll(spec, pageable);
        logger.debug("GET: getAllMedico, Consulta de médicos");
        return ResponseEntity.status(HttpStatus.OK).body(medicoModelPage);
    }

    /**
     * Atualiza os dados de um médico específico.
     *
     * @param medicoId ID do médico a ser atualizado.
     * @param medicoRecordDto DTO contendo os novos dados do médico.
     * @return ResponseEntity com os dados atualizados do médico.
     */
    @PutMapping("/{medicoId}/medico")
    public ResponseEntity<Object> updateMedico(@PathVariable(value = "medicoId") UUID medicoId,
                                               @RequestBody @Validated(MedicoRecordDto.MedicoView.MedicoPut.class)
                                               MedicoRecordDto medicoRecordDto) {
        logger.debug("PUT: updateMedico, Dados do médico atualizado com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(medicoService.updateMedico(medicoService.findById(medicoId).get(), medicoRecordDto));
    }

    /**
     * Exclui um médico do sistema, desde que ele não esteja vinculado a consultas.
     *
     * @param medicoId ID do médico a ser excluído.
     * @return ResponseEntity com a mensagem de sucesso ou erro.
     */
    @DeleteMapping("/{medicoId}")
    public ResponseEntity<Object> deleteMedico(@PathVariable(value = "medicoId") UUID medicoId) {
        if (consultaService.existsByMedicoConsultaMedicoId(medicoId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Não é possível excluir o médico, pois ele está associado a uma ou mais consultas.");
        }
        medicoService.deleteByMedicoId(medicoService.findById(medicoId).get());
        logger.debug("DELETE: deleteByMedicoId, Cadastro de médico deletado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body("Cadastro de médico deletado com sucesso!");
    }
}