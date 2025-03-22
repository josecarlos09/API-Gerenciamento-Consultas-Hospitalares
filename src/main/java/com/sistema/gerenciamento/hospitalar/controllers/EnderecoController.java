package com.sistema.gerenciamento.hospitalar.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.gerenciamento.hospitalar.cliente.ViaCepService;
import com.sistema.gerenciamento.hospitalar.dtos.EnderecoRecordDto;
import com.sistema.gerenciamento.hospitalar.models.EnderecoModel;
import com.sistema.gerenciamento.hospitalar.services.EnderecoService;
import com.sistema.gerenciamento.hospitalar.services.FuncionarioService;
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
import reactor.core.publisher.Mono;

import java.util.UUID;
/**
 * Controlador para gerenciar operações relacionadas a endereços de funcionários e pacientes.
 * Inclui endpoints para criação, leitura, atualização e exclusão de endereços.
 */
@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    Logger logger = LogManager.getLogger(EnderecoController.class);

    final EnderecoService enderecoService;
    final FuncionarioService funcionarioService;
    final PacienteService pacienteService;
    final ViaCepService viaCepService;

    public EnderecoController(EnderecoService enderecoService, FuncionarioService funcionarioService,
                              PacienteService pacienteService, ViaCepService viaCepService) {
        this.enderecoService = enderecoService;
        this.funcionarioService = funcionarioService;
        this.pacienteService = pacienteService;
        this.viaCepService = viaCepService;
    }

    /**
     * Registra um endereço para um funcionário específico.
     */
    @PostMapping("/{funcionarioId}/funcionario")
    public ResponseEntity<Object> saveEnderecoFuncionario(
            @RequestBody @Validated(EnderecoRecordDto.EnderecoView.EnderecoPost.class)
            @JsonView(EnderecoRecordDto.EnderecoView.EnderecoPost.class)
            EnderecoRecordDto enderecoRecordDto,
            @PathVariable(value = "funcionarioId") UUID funcionarioId) {
        logger.debug("POST: Registro do endereco para o funcionário {}", enderecoRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(enderecoService.saveEnderecoFuncionario(enderecoRecordDto, funcionarioService.findById(funcionarioId).get()));
    }

    /**
     * Registra um endereço para um paciente específico.
     */
    @PostMapping("/{pacienteId}/paciente")
    public ResponseEntity<Object> registroEnderecoPaciente(
            @RequestBody @Validated(EnderecoRecordDto.EnderecoView.EnderecoPost.class)
            @JsonView(EnderecoRecordDto.EnderecoView.EnderecoPost.class)
            EnderecoRecordDto enderecoRecordDto,
            @PathVariable(value = "pacienteId") UUID pacienteId) {
        logger.debug("POST: Registro do endereco para o paciente {}", enderecoRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(enderecoService.saveEnderecoPaciente(enderecoRecordDto, pacienteService.findById(pacienteId).get()));
    }

    /**
     * Busca informações de um endereço a partir de um CEP.
     */
    @GetMapping("/{cep}/cep")
    public Mono<ResponseEntity<?>> buscarCep(@PathVariable String cep) {
        return viaCepService.consultarCep(cep)
                .flatMap(endereco -> {
                    if (endereco == null) {
                        return Mono.just(ResponseEntity.status(404).body("CEP não encontrado."));
                    }
                    return Mono.just(ResponseEntity.ok(endereco));
                })
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(500).body("ERRO: " + ex.getMessage())));
    }

    /**
     * Retorna uma lista paginada de endereços com filtros opcionais.
     */
    @GetMapping
    public ResponseEntity<Page<EnderecoModel>> getAllEndereco(SpecificationsTemplate.EnderecoSpec spec,
                                                              Pageable pageable) {
        Page<EnderecoModel> enderecoModel = enderecoService.fidAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(enderecoModel);
    }

    /**
     * Retorna informações detalhadas de um endereço específico pelo ID.
     */
    @GetMapping("/{enderecoId}")
    public ResponseEntity<Object> getOnEndereco(@PathVariable(value = "enderecoId") UUID enderecoId) {
        logger.debug("GET ONE: Consulta de endereço!");
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.findById(enderecoId));
    }

    /**
     * Atualiza as informações de um endereço existente pelo ID.
     */
    @PutMapping("/{enderecoId}")
    public ResponseEntity<Object> updateEndereco(@PathVariable(value = "enderecoId") UUID enderecoId,
                                                 @RequestBody @Validated(EnderecoRecordDto.EnderecoView.EnderecoPut.class)
                                                 @JsonView(EnderecoRecordDto.EnderecoView.EnderecoPut.class)
                                                 EnderecoRecordDto enderecoRecordDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(enderecoService.updateEndereco(enderecoRecordDto, enderecoService.findById(enderecoId).get()));
    }

    /**
     * Exclui um endereço pelo ID.
     */
    @DeleteMapping("/{enderecoId}")
    public ResponseEntity<Object> deleteEndereco(@PathVariable(value = "enderecoId") UUID enderecoId) {
        enderecoService.deleteEndereco(enderecoService.findById(enderecoId).get());
        return ResponseEntity.status(HttpStatus.OK).body("ENDEREÇO DELETADO COM SUCESSO!");
    }
}