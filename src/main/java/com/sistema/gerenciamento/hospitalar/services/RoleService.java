package com.sistema.gerenciamento.hospitalar.services;

import com.sistema.gerenciamento.hospitalar.enums.RoleType;
import com.sistema.gerenciamento.hospitalar.models.RoleModel;

public interface RoleService {

    // Método para buscar um modelo de role (papel de usuário) com base no nome do papel (RoleType).
    // O parâmetro 'roleType' é utilizado para identificar o tipo de papel desejado (ex: ADMIN, USER, etc.).
    // Retorna o modelo de role correspondente ou uma exceção, caso não encontrado.
    RoleModel findByRoleNome(RoleType roleType);
}