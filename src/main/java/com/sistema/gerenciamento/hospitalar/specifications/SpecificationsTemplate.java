package com.sistema.gerenciamento.hospitalar.specifications;

import com.sistema.gerenciamento.hospitalar.models.*;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
public class SpecificationsTemplate {

    // Mapeamento dos filtros dinâmicos para a entidade UsuarioModel
    // A interface UsuarioSpec define os filtros de busca que podem ser aplicados sobre os atributos da entidade UsuarioModel
    @And({
            @Spec(path = "usuarioId", spec = Equal.class), // Filtro de igualdade para o atributo usuarioId
            @Spec(path = "codigoUsuario", spec = Equal.class), // Filtro de igualdade para o atributo codigoUsuario
            @Spec(path = "nome", spec = Like.class), // Filtro de busca parcial para o atributo nome (LIKE)
    })
    public interface UsuarioSpec extends Specification<UsuarioModel> {}

    // Mapeamento dos filtros dinâmicos para a entidade PacienteModel
    // A interface PacienteSpec define os filtros de busca que podem ser aplicados sobre os atributos da entidade PacienteModel
    @And({
            @Spec(path = "pacienteId", spec = Equal.class), // Filtro de igualdade para o atributo pacienteId
            @Spec(path = "cpf", spec = Equal.class), // Filtro de igualdade para o atributo cpf
            @Spec(path = "rg", spec = Equal.class), // Filtro de igualdade para o atributo rg
            @Spec(path = "nomeCompleto", spec = LikeIgnoreCase.class), // Filtro de busca parcial para o nome, sem considerar maiúsculas/minúsculas
            @Spec(path = "statusPaciente", spec = Equal.class), // Filtro de igualdade para o atributo statusPaciente
    })
    public interface PacienteSpec extends Specification<PacienteModel> {}

    // Mapeamento dos filtros dinâmicos para a entidade FuncionarioModel
    // A interface FuncionarioSpec define os filtros de busca que podem ser aplicados sobre os atributos da entidade FuncionarioModel
    @And({
            @Spec(path = "funcionarioId", spec = Equal.class), // Filtro de igualdade para o atributo funcionarioId
            @Spec(path = "cpf", spec = Equal.class), // Filtro de igualdade para o atributo cpf
            @Spec(path = "rg", spec = Equal.class), // Filtro de igualdade para o atributo rg
            @Spec(path = "nomeCompleto", spec = LikeIgnoreCase.class), // Filtro de busca parcial para o nome, sem considerar maiúsculas/minúsculas
            @Spec(path = "statusFuncionario", spec = Equal.class), // Filtro de igualdade para o atributo statusFuncionario
    })
    public interface FuncionarioSpec extends Specification<FuncionarioModel> {}

    // Mapeamento dos filtros dinâmicos para a entidade EnderecoModel
    // A interface EnderecoSpec define os filtros de busca que podem ser aplicados sobre os atributos da entidade EnderecoModel
    @And({
            @Spec(path = "cep", spec = Equal.class), // Filtro de igualdade para o atributo cep
    })
    public interface EnderecoSpec extends Specification<EnderecoModel> {}

    // Mapeamento dos filtros dinâmicos para a entidade MedicoModel
    // A interface MedicoSpec define os filtros de busca que podem ser aplicados sobre os atributos da entidade MedicoModel
    @And({
            @Spec(path = "crn", spec = Equal.class), // Filtro de igualdade para o atributo crn
            @Spec(path = "cns", spec = Equal.class) // Filtro de igualdade para o atributo cns
    })
    public interface MedicoSpec extends Specification<MedicoModel>{}

    // Mapeamento dos filtros dinâmicos para a entidade ConsultaModel
    // A interface ConsultaSpec define os filtros de busca que podem ser aplicados sobre os atributos da entidade ConsultaModel
    @And({
            @Spec(path = "valorConsulta", spec = Equal.class), // Filtro de igualdade para o atributo valorConsulta
            @Spec(path = "tipoAtendimento", spec = Equal.class) // Filtro de igualdade para o atributo tipoAtendimento
    })
    public interface ConsultaSpec extends Specification<ConsultaModel>{}

    // Mapeamento dos filtros dinâmicos para a entidade ClinicaHospitalarModel
    // A interface ClinicaSpec define os filtros de busca que podem ser aplicados sobre os atributos da entidade ClinicaHospitalarModel
    @And({
            @Spec(path = "nome", spec = Equal.class), // Filtro de igualdade para o atributo nome
            @Spec(path = "cnpj", spec = Equal.class), // Filtro de igualdade para o atributo cnpj
            @Spec(path = "telefone", spec = Equal.class) // Filtro de igualdade para o atributo telefone
    })
    public interface ClinicaSpec extends Specification<ClinicaHospitalarModel>{}
}