package com.sistema.gerenciamento.hospitalar.services;

import com.sistema.gerenciamento.hospitalar.dtos.ClinicaHospitalarRecordDto;
import com.sistema.gerenciamento.hospitalar.models.ClinicaHospitalarModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface ClinicaHospitalarService {

    // Método para salvar uma nova clínica hospitalar. Recebe um DTO (Data Transfer Object) com as informações da clínica.
    ClinicaHospitalarModel saveClinicaHospitalar(ClinicaHospitalarRecordDto clinicaHospitalarRecordDto);

    // Método para buscar todas as clínicas hospitalares com suporte a filtros dinâmicos e paginação.
    // O parâmetro 'spec' permite construir consultas dinâmicas, e 'pageable' define a página e o tamanho da página para paginar os resultados.
    Page<ClinicaHospitalarModel> fidAll(Specification<ClinicaHospitalarModel> spec, Pageable pageable);

    // Método para buscar uma clínica hospitalar pelo seu ID único (UUID). Retorna um Optional para evitar NullPointerExceptions caso o ID não seja encontrado.
    Optional<ClinicaHospitalarModel> findById(UUID clinicaId);

    // Método para atualizar uma clínica hospitalar. Recebe o DTO com os dados atualizados e o modelo da clínica para ser modificado.
    ClinicaHospitalarModel atualizarClinicaHospitalar(ClinicaHospitalarRecordDto clinicaHospitalarRecordDto, ClinicaHospitalarModel clinicaHospitalarModel);

    // Método para deletar uma clínica hospitalar pelo modelo da clínica.
    void deleteByClinicaId(ClinicaHospitalarModel clinicaHospitalarModel);

    // Método para verificar se já existe uma clínica com o mesmo nome.
    boolean existByNome(String nome);

    // Método para verificar se já existe uma clínica com o mesmo CNPJ.
    boolean existByCnpj(String cnpj);

    // Método para verificar se já existe uma clínica com o mesmo email.
    boolean existByEmail(String email);
}