package com.sistema.gerenciamento.hospitalar.services.impl;

import com.sistema.gerenciamento.hospitalar.enums.RoleType;
import com.sistema.gerenciamento.hospitalar.models.RoleModel;
import com.sistema.gerenciamento.hospitalar.repositorys.RoleRepository;
import com.sistema.gerenciamento.hospitalar.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    // Repositório responsável por manipular os dados das roles (funções/permissões)
    final RoleRepository roleRepository;

    // Construtor para injeção de dependência do repositório
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Método para encontrar uma role pelo nome da role.
     *
     * @param roleType Tipo de role que estamos procurando, representado pelo enum RoleType.
     * @return RoleModel Retorna o modelo de role encontrado.
     * @throws RuntimeException Se a role não for encontrada, lança uma exceção.
     */
    @Override
    public RoleModel findByRoleNome(RoleType roleType) {
        // Tenta encontrar a role no banco de dados através do nome (RoleType)
        return roleRepository.findByRoleNome(roleType)
                // Se não encontrar, lança uma exceção personalizada com a mensagem de erro
                .orElseThrow(() -> new RuntimeException("ERROR: ROLE NÃO EXISTENTE."));
    }
}