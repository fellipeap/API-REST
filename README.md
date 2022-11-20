# Sobre o projeto

Esta é uma API simples de controle de cadastro de clientes, onde é possível realizar algumas manipulações básicas de dados, como: cadastrar, excluir, alterar, pesquisar e listar todos os clientes cadastrados.

O principal objetivo desta aplicação consiste em implementar de forma coerente tais recursos, focando na organização do código, simplicidade da solução e cobertura dos testes.
 
Por padrão, o arquivo de configuração “*.properties” ativo é o “application-local.properties” útil em ambiente de testes, pois já incluirá automaticamente no banco de dados h2 alguns registros definidos no arquivo “/configs/LocalConfig.java”.

O **H2 database**, armazena os dados de forma temporária e sempre que o projeto for reiniciado será recarregado os dados iniciais, logo também não será necessário configurações adicionais após a importação do projeto. 

# Tecnologias utilizadas
## Aplicação
- Java 17
- Spring Boot
- JPA / Hibernate
- Maven
- Spring validation
- Spring web
- h2database
- Lombok
- Springdoc / Swagger

# Como executar o projeto

## Pré-requisito: 
 Java 17

```bash
# clonar repositório
git clone https://github.com/Fellipe-Aparecido/kyrosb-01.git

# importe pela IDE de sua preferência a pasta kyrosb-01
# Pode ocorrer da IDE não fazer o download automático das dependências especificadas no arquivo pom.xml, 
# neste caso, será necessário forçar a sincronização ou reiniciar a IDE.

# Após o projeto importado e as dependências baixadas o **projeto** e os **testes** poderão ser executados normalmente pela própria IDE.
# Diretório do projeto
 \src\main\
 
# Diretório dos testes
 \src\test\
```

# End Point

## Base URL
 http://localhost:8080/customer/

## Importação da collection com Postman
```bash
 https://www.getpostman.com/collections/02727a4bd070727d9535
 ```
## Acesso com Swagger
```bash
 http://localhost:8080/swagger-ui/index.html?#/
```
## Servidor de testes online
https://api-fellipe.herokuapp.com/customer
