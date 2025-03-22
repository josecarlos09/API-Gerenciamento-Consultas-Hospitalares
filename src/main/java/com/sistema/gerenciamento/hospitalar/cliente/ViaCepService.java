package com.sistema.gerenciamento.hospitalar.cliente;

import com.sistema.gerenciamento.hospitalar.dtos.EnderecoRecordDto;
import com.sistema.gerenciamento.hospitalar.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


/**
 * Classe responsável por realizar a consulta de um endereço via API do ViaCEP.
 * Utiliza o WebClient do Spring WebFlux para fazer chamadas HTTP de forma assíncrona.
 */
@Service
public class ViaCepService {
    // Instância do WebClient para realizar requisições à API ViaCEP
    private final WebClient webClient;

    /**
     * Construtor que inicializa o WebClient com a URL base do serviço ViaCEP.
     *
     * @param webClientBuilder O WebClient.Builder fornecido pelo Spring para configurar o WebClient.
     */
    public ViaCepService(WebClient.Builder webClientBuilder) {
        // Configura a URL base para as requisições
        this.webClient = webClientBuilder.baseUrl("https://viacep.com.br/ws").build();
    }

    /**
     * Realiza a consulta de um endereço pelo CEP via API do ViaCEP.
     *
     * @param cep O CEP que será consultado.
     * @return Mono<EnderecoRecordDto> Um Mono contendo os dados do endereço ou um erro caso ocorra.
     */
    public Mono<EnderecoRecordDto> consultarCep(String cep) {
        // Realiza uma requisição GET para a URL '/{cep}/json/', substituindo o {cep} pelo valor fornecido.
        return webClient.get()
                .uri("/{cep}/json/", cep) // URI dinâmica para o CEP
                .retrieve() // Executa a requisição
                // Verifica se o status da resposta é de erro (status >= 400)
                .onStatus(status -> status.isError(), clientResponse -> {
                    // Caso haja erro, retorna um Mono com uma exceção personalizada.
                    return Mono.error(new NotFoundException("CEP INVÁLIDO"));
                })
                // Mapeia o corpo da resposta para o DTO EnderecoRecordDto
                .bodyToMono(EnderecoRecordDto.class)
                // Se ocorrer erro específico da API ViaCEP, trata com uma exceção customizada
                .onErrorResume(WebClientResponseException.class, ex -> {
                    // Retorna um Mono com uma mensagem de erro para falhas específicas da API ViaCEP
                    return Mono.error(new RuntimeException("Erro na comunicação com a API ViaCep: " + ex.getMessage()));
                })
                // Se ocorrer qualquer outro tipo de erro, trata de forma genérica
                .onErrorResume(Exception.class, ex -> {
                    // Retorna um Mono com uma exceção genérica
                    return Mono.error(new RuntimeException("" + ex.getMessage()));
                });
    }
}