package com.sistema.gerenciamento.hospitalar.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.gerenciamento.hospitalar.dtos.FuncionarioRecordDto;
import com.sistema.gerenciamento.hospitalar.models.FuncionarioModel;
import com.sistema.gerenciamento.hospitalar.services.ConsultaService;
import com.sistema.gerenciamento.hospitalar.services.EnderecoService;
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
 * Controlador para gerenciar operações relacionadas aos funcionários.
 */
@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    Logger logger = LogManager.getLogger(FuncionarioController.class); // Logger para registrar eventos

    final FuncionarioService funcionarioService;
    final EnderecoService enderecoService;
    final MedicoService medicoService;
    final ConsultaService consultaService;

    public FuncionarioController(FuncionarioService funcionarioService, EnderecoService enderecoService, MedicoService medicoService, ConsultaService consultaService) {
        this.funcionarioService = funcionarioService;
        this.enderecoService = enderecoService;
        this.medicoService = medicoService;
        this.consultaService = consultaService;
    }

    /**
     * Registra um novo funcionário.
     * @param funcionarioRecordDto Dados do funcionário a ser registrado.
     * @return ResponseEntity contendo o funcionário registrado ou erro em caso de duplicidade.
     */
    @PostMapping
    public ResponseEntity<Object> registroFuncionario(@RequestBody @Validated(FuncionarioRecordDto.FuncionarioView.RegistroFuncionarioPost.class)
                                                      @JsonView(FuncionarioRecordDto.FuncionarioView.RegistroFuncionarioPost.class)
                                                      FuncionarioRecordDto funcionarioRecordDto){
        if (funcionarioService.existByNomeCompleto(funcionarioRecordDto.nomeCompleto())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, FUNCIONARIO JÁ EXISTENTE!");
        }
        if (funcionarioService.existByCpf(funcionarioRecordDto.cpf())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, ESSE CPF JÁ ESTÁ EM USO!");
        }
        if (funcionarioService.existByRg(funcionarioRecordDto.rg())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, ESSE RG JÁ EXISTE!");
        }
        logger.debug("POST: Registro de funcionario {}", funcionarioRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.saveFuncionario(funcionarioRecordDto));
    }

    /**
     * Obtém uma lista paginada de funcionários.
     * @param spec Filtro de busca para funcionários.
     * @param pageable Paginação dos resultados.
     * @return ResponseEntity contendo a lista paginada de funcionários.
     */
    @GetMapping
    public ResponseEntity<Page<FuncionarioModel>> getAllFuncionario(SpecificationsTemplate.FuncionarioSpec spec,
                                                                    Pageable pageable){
        Page<FuncionarioModel> funcionarioModel = funcionarioService.fidAll(spec, pageable);
        logger.debug("GET: Consulta de funcionarios");
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioModel);
    }

    /**
     * Obtém os detalhes de um funcionário específico.
     * @param funcionarioId ID do funcionário.
     * @return ResponseEntity contendo os detalhes do funcionário.
     */
    @GetMapping("/{funcionarioId}")
    public ResponseEntity<Object> getOnFuncionario(@PathVariable(value = "funcionarioId")UUID funcionarioId){
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.findById(funcionarioId));
    }

    /**
     * Atualiza os dados de um funcionário existente.
     * @param funcionarioId ID do funcionário a ser atualizado.
     * @param funcionarioRecordDto Novos dados do funcionário.
     * @return ResponseEntity contendo o funcionário atualizado.
     */
    @PutMapping("/{funcionarioId}")
    public ResponseEntity<Object> updateFuncionario(@PathVariable(value = "funcionarioId")UUID funcionarioId,
                                                    @RequestBody @Validated (FuncionarioRecordDto.FuncionarioView.FuncionarioPut.class)
                                                    @JsonView(FuncionarioRecordDto.FuncionarioView.FuncionarioPut.class)
                                                    FuncionarioRecordDto funcionarioRecordDto){
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.updateFuncionario(funcionarioRecordDto, funcionarioService.findById(funcionarioId).get()));
    }

    /**
     * Remove um funcionário do sistema.
     * @param funcionarioId ID do funcionário a ser removido.
     * @return ResponseEntity com mensagem de sucesso.
     */
    @DeleteMapping("/{funcionarioId}")
    public ResponseEntity<Object> deleteFuncionario(@PathVariable(value = "funcionarioId") UUID funcionarioId) {
        if (medicoService.existsByFuncionarioMedicoFuncionarioId(funcionarioId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Não é possível excluir o funcionario, pois ele é um médico.");
        }
        funcionarioService.deleteFuncionario(funcionarioService.findById(funcionarioId).get());
        return ResponseEntity.status(HttpStatus.OK).body("CADASTRO DE FUNCIONARIO DELETADO COM SUCESSO!");
    }

    /**
     * Atualiza o status de um funcionário.
     * @param funcionarioId ID do funcionário.
     * @param funcionarioRecordDto Novo status do funcionário.
     * @return ResponseEntity contendo o funcionário atualizado.
     */
    @PutMapping("/{funcionarioId}/status")
    public ResponseEntity<Object> updateStatus(@PathVariable(value = "funcionarioId") UUID funcionarioId,
                                               @RequestBody @Validated(FuncionarioRecordDto.FuncionarioView.StatusFuncionarioPut.class)
                                               FuncionarioRecordDto funcionarioRecordDto){
        logger.debug("PUT: status do usuário atualizado com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.updateStatusFuncionario(funcionarioService.findById(funcionarioId).get(), funcionarioRecordDto));
    }
}