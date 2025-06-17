# Projeto: Sistema Web de Leilão
## Tecnologias
- **Maven**
- **Java**
- **Spring Boot**
- **JPA**
- **Hibernate**
- **Lombok**

## Explicando Estrutura
- **src** --> pacote que contém todo o código fonte 
- **main** --> pacote principal da aplicação
- **java** --> classes *.java*
- **resouces** --> arquivos de configuração
- **properities** --> contem configurações do banco
- **pom.xml** --> contém as configurações do maven e as dependências

## Padrões Utilizados
- **/config** --> contem classes *Config* que configuram a aplicação Spring Boot
- **/controller** --> contem classes *Controller* que tratam requisições e definem endpoints
- **/exception** --> contem classes *Exception* que tratam exceções com mensagens personalizadas
- **/model** --> contem classes de *Entity* (classes para tabelas do banco) e *DTO* (transferência de dados) 
- **/repository** --> contem classes *Repository* que implementam interfaces para acessar o banco
- **/security** --> contem arquivos e classes *Security* que enolvem a segurança da aplicação
- **/service** --> contem classes *Service* que aplicam as regras de negócio da aplicação

## Notações Utilizadas
- **@Data** --> gerar getters, setters, toString(), equals(), hashCode() e um construtor para os campos finais
- **@RestController** --> define que uma classe Controller REST
- **@GetMapping** --> define um mapeamento de uma requisição GET
- **@PostMapping** --> define o mapeamento de uma requisição POST
- **@PutMapping** --> define o mapeamento de uma requisição PUT
- **@RequestParam** --> define um parâmetro da requisição
- **@RequestBody** --> define que um corpo da requisição deve ser convertido em uma Classe Java
- **@Service** --> define uma classe Service registrando no conteiner de injeção de dependência
- **@Autowired** --> define a injeção automática de uma dependência quando for necessário
- **@NotBlank** --> define um atributo como obrigatório
- **@Email** --> define um campo como email para a validação
- **@CPF** --> defini um campo como CPF para a validação
- **@Valid** --> usado para validar uma entidade de entrada em uma requisição POST
- **@PathParam** --> define um parâmetro incluso no caminho

## Injeção de Dependência
- Pode ser feita por um construtor ou usando a notação @Autowired

## Persistência
- Adicionar a dependência do banco 
- Adicionar o *Spring Data* e *MySQL Driver* (Spring Initialzr)

## Dicas de Configuração
- dividir configurações públicas e secretas em arquivos diferentes

## Funcionamento do Maven
- Cria um repositório local para dependências *.m2*

## Comandos para Executar
- **.\mvnw install** --> compila, testa e instala um projeto *Java* baseado *Maven* usando um *Maven Wrapper* 
- executar **run** no arquivo main da aplicação
- acessar o endereço **localhost:8080/backend.andreyjodar.github.com**

## Ferramentas Auxiliares
- **Insomnia** ou **Postman** --> usado para testar requisições POST
- **XAMP** --> usado para fazer contado com o banco e o servidor
- **phpMyAdmin** --> servidor utilizado para o projeto

## Arquitetura Utilizada
- **Client:** Postman ou Front
- **Controller:** controlador de requisições
- **Service:** executa operações envolvendo model e repository
- **Model:** contém as entidades que serão persistidas
- **Repository:** manipula o banco de dados (interface)