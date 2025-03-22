package com.sistema.gerenciamento.hospitalar.repositorys;

import com.sistema.gerenciamento.hospitalar.models.ClinicaHospitalarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

// Define a interface do repositório para a entidade ClinicaHospitalarModel
public interface ClinicaHospitalarRepository extends JpaRepository<ClinicaHospitalarModel, UUID>, JpaSpecificationExecutor<ClinicaHospitalarModel> {

    // Método para verificar se já existe uma clínica hospitalar com o nome fornecido
    boolean existsByNome(String nome);

    // Método para verificar se já existe uma clínica hospitalar com o CNPJ fornecido
    boolean existsByCnpj(String cnpj);

    // Método para verificar se já existe uma clínica hospitalar com o e-mail fornecido
    boolean existsByEmail(String email);
}