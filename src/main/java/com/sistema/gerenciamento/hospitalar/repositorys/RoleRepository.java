package com.sistema.gerenciamento.hospitalar.repositorys;

import com.sistema.gerenciamento.hospitalar.enums.RoleType;
import com.sistema.gerenciamento.hospitalar.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

// Define a interface do repositório para a entidade RoleModel
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    // Método para buscar uma RoleModel a partir do nome do tipo de role (RoleType)
    Optional<RoleModel> findByRoleNome(RoleType nome);
}