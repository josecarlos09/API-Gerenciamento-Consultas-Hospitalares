
# API para Gerenciamento de Agendamentos de Consultas Médicas
A API da **Technology Soluções Empresariais** é responsável por gerenciar o agendamento de consultas hospitalares. O sistema permite que médicos vinculados a uma clínica realizem atendimentos a pacientes previamente cadastrados, garantindo um controle eficiente das consultas. Além disso, a API possui autenticação baseada em Token JWT e um controle de acessos conforme os diferentes perfis de usuários.

## Documentação da API com Swagger
http://localhost:8087/clinica_hospitalar/swagger-ui/swagger-ui/index.html

Após o serviço estar no ar, acesse o Swagger para visualizar apara visualizar a documentação e testar a API de forma interativa. O Swagger gera automaticamente os detalhes dos endpoints, permitindo explorar, validar e executar requisições diretamente pela interface gráfica. Isso facilita o desenvolvimento, a manutenção e a integração com outros serviços.

## Modelo inicial da aplicação:

https://www.figma.com/design/N4CgpJqsg7gjbKuDmra3EV/Voll.med?node-id=2-1007&p=f&t=Seshqdm85oC04bAz-0

## Tecnologias Utilizadas
•	**Java 21**

•	**Spring Boot 3.3.5**

•	**Spring MVC**

•	**Maven**

•	**Configurações em arquivo .yaml**

•	**Spring Data JPA**

•	**Spring Security, Token JWT, uso de ROLEs e criptografia de senhas na base de dados**

• **Swagger**

• **Specification**

• **Validation**

•  **Log4j2**

•	**PostgreSQL**

•	**Uso da API do VIACEP**


# Funcionalidades Principais
## 1. Gerenciamento de Usuários e Funcionários
•	Cadastro, atualização e exclusão de funcionários.

•	Funcionários podem ser médicos ou administrativos.

•	Apenas ADMIN e SECRETARIA podem gerenciar usuários e funcionários.

•	Remoção de funcionários implica na exclusão automática do usuário associado e seus dados vinculados.

## 2. Gerenciamento de Médicos

•	Médicos são um tipo especial de funcionário.

•	Cada médico possui CRM e CNS únicos.

•	Médicos devem estar vinculados a uma especialidade.

•	Um médico com consultas registradas não pode ser removido sem que as consultas sejam resolvidas ou excluídas.


## 3. Gerenciamento de Pacientes
•	Cadastro, atualização e exclusão de pacientes.

•	Controle rigoroso de unicidade para CPF, RG, telefone e CNS.

•	Médicos podem atualizar informações médicas dos pacientes.

## 4. Gerenciamento de Endereços

•	Médicos e pacientes podem ter múltiplos endereços cadastrados.

•	Campos obrigatórios: país, estado (UF), cidade, número, CEP.

•	Campo opcional: complemento.

## 5. Gerenciamento de Clínicas

•	Cadastro e manutenção de clínicas com CNPJ, telefone e e-mail.

•	Definição de horário de funcionamento (08:00 - 18:00).

## 6. Agendamento de Consultas

•	Apenas médicos ATIVOS e pacientes ATIVOS podem realizar consultas.

•	A consulta tem uma duração média de 30 minutos.

•	O sistema verifica se o médico já possui consultas no horário solicitado.

•	Agendamentos devem ser feitos com no mínimo 30 minutos de antecedência.

•	Cancelamentos sem cobrança devem ocorrer com pelo menos 24 horas de antecedência.

•	Apenas SECRETARIA e ADMIN podem agendar e cancelar consultas.

## 7. Gestão das Consultas

•	Registro de status da consulta: ATENDIDO, CANCELADO, REMARCADO.

•	Médicos podem adicionar diagnósticos e observações.

## 8. Autenticação e Controle de Acesso

•	Usuários se autenticam com nome de usuário, senha e código da clínica.

•	Geração de token JWT.

•	Perfis de acesso:

- ADMIN: Controle total sobre o sistema.

- SECRETARIA: Gerenciamento de consultas, pacientes e médicos.

- MÉDICO: Atualização de informações médicas e consulta de pacientes.

- USUÁRIO: Apenas acesso básico e visualização de informações limitadas.

## Segurança e Regras de uso

•	Senhas são armazenadas de forma segura utilizando criptografia.

•	O sistema valida permissões de usuários antes de permitir operações.

•	Apenas usuários autorizados podem cadastrar ou excluir registros sensíveis.

•	Regras de negócio garantem que médicos e pacientes ativos sejam necessários para agendar consultas.

•	Validações de dados de pacientes e médicos 

## Conclusão
Essa API fornecerá um ambiente seguro e eficiente para gerenciar o fluxo de consultas médicas dentro das clínicas cadastradas. Com validações rigorosas e uma estrutura de controle de acessos bem definida, o sistema garantirá confiabilidade e integridade nos atendimentos realizados.

