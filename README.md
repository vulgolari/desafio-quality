# Desafio QA

Este repositório contém o projeto desenvolvido como parte do Desafio QA. O objetivo do projeto é testar uma API REST usando o framework RestAssured, em conjunto com o JUnit para execução dos testes. Os testes abrangem métodos de requisição GET, POST, PUT, PATCH e DELETE.

## Tecnologias Utilizadas

- **Java 11**: Linguagem de programação utilizada para o desenvolvimento dos testes.
- **Maven**: Ferramenta de automação de compilação e gerenciamento de dependências.
- **JUnit 5**: Framework de testes unitários para Java.
- **RestAssured**: Biblioteca para testes de APIs RESTful.
- **Hamcrest**: Biblioteca para facilitar a escrita de asserts em testes.

## Estrutura do Projeto

A estrutura do projeto é organizada da seguinte forma:

```
desafioQa/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ... (código-fonte principal)
│   ├── test/
│   │   ├── java/
│   │   │   └── desafioqa/
│   │   │       ├── JsonPlaceholderTest.java
│   │   │       └── ...
│   ├── resources/
│   │   ├── ... (recursos para testes e configurações)
├── .gitignore
├── pom.xml
└── README.md
```

## Instruções para Configuração e Execução

### Pré-requisitos

- **Java 11** ou superior
- **Maven** instalado

### Como Executar

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/vulgolari/desafioQa.git
   cd desafioQa
   ```

2. **Instale as dependências:**

   ```bash
   mvn clean install
   ```

3. **Execute os testes:**

   Para executar todos os testes, utilize o seguinte comando:

   ```bash
   mvn test
   ```

   Os resultados dos testes serão exibidos no console e os relatórios podem ser encontrados na pasta `target/surefire-reports`.

## Estrutura dos Testes

- **JsonPlaceholderTest**: Testes para a API JSONPlaceholder, incluindo métodos de requisição GET, POST, PUT, PATCH e DELETE.
- **PokeApiTest**: Testes para a API PokeAPI, incluindo métodos de requisição GET.

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests. Para contribuições maiores, por favor, discuta primeiro as mudanças que deseja fazer por meio de uma issue.

## Licença

Este projeto é distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
