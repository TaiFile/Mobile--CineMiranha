# 🎬 POOA Cinema API

Este projeto é parte do trabalho da disciplina de **Programação Orientada a Objetos Avançada (POOA)**.

## 📘 Descrição

API REST para gerenciamento de compra de ingressos em cinemas, desenvolvida com foco em estudos de arquitetura e boas práticas de desenvolvimento.

## 🧱 Arquitetura

O projeto utiliza **Vertical Slice Architecture (VSA)** combinada com princípios de **Clean Architecture**:

* **Vertical Slice Architecture (VSA)**: Organização por features/funcionalidades ao invés de camadas técnicas
* **Domain-Driven Design (DDD)**: Entidades e repositórios no domínio
* **Princípios SOLID**
* **Padrões de projeto**: Injeção de Dependência, Inversão de Dependência, Strategy Pattern
* **Uso extensivo de interfaces para desacoplamento**

### 📂 Estrutura do Projeto

```
src/main/java/br/ufscar/pooa/cinema_api/
├── domain/                    # 🏛️ Núcleo do domínio
│   ├── entities/              # Entidades de negócio (Movie, Session, Ticket...)
│   ├── enums/                 # Enumerações (AgeRating, Format, Role...)
│   └── repositories/          # Interfaces dos repositórios (ports)
│
├── features/                  # 🎯 Vertical Slices (funcionalidades)
│   ├── _shared/               # Componentes compartilhados entre features
│   │   ├── exceptions/        # Exceções customizadas e handlers
│   │   ├── gateways/          # Interfaces para serviços externos
│   │   └── validation/        # Validadores customizados
│   ├── movies/                # Feature: Filmes
│   │   ├── controller/        # Endpoints REST
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── mapper/            # Mapeadores Entity <-> DTO
│   │   └── usecase/           # Casos de uso (regras de negócio)
│   ├── sessions/              # Feature: Sessões
│   ├── tickets/               # Feature: Ingressos
│   ├── theaters/              # Feature: Cinemas
│   ├── rooms/                 # Feature: Salas
│   ├── clients/               # Feature: Clientes
│   ├── managers/              # Feature: Gerentes
│   └── admins/                # Feature: Administradores
│
├── infrastructure/            # 🔧 Infraestrutura e configurações
│   ├── config/                # Configurações (Security, OpenAPI, Async)
│   ├── database/              # Seeders e configurações de banco
│   └── providers/             # Implementações de serviços externos
│       ├── email/             # Provider de envio de emails
│       └── payment/           # Provider de pagamentos
│
└── utils/                     # 🛠️ Utilitários
```

> 📖 Para uma explicação detalhada da arquitetura, consulte [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)

## 🚀 Tecnologias Utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* Apache Maven
* Docker & Spring Boot Docker Compose (PostgreSQL)
* Springdoc OpenAPI (Swagger UI)

## 🛠️ Como Executar o Projeto

### Pré-requisitos

* Java 21 instalado
* Apache Maven instalado
* Docker instalado e em execução (Docker Desktop, por exemplo)

### Passos para execução

1. Clone o repositório:

   ```bash
   git clone https://github.com/eduMalagutti/POOA-cinema-api.git
   cd POOA-cinema-api/backend
   ```

2. Compile e execute a aplicação (o próprio Spring Boot, via `spring-boot-docker-compose`, sobe os serviços definidos no `docker-compose.yml` automaticamente):

   ```bash
   mvn spring-boot:run
   ```

3. Acesse a API em:

   ```
   http://localhost:8080
   ```

4. Acesse a documentação Swagger/OpenAPI em:

   ```
   http://localhost:8080/swagger-ui/index.html
   ```

## 🧪 Testes

Para executar os testes automatizados:

```bash
mvn test
```

## 📚 Documentação

- [Arquitetura do Projeto](docs/ARCHITECTURE.md)
- Documentação interativa da API (Swagger UI) em `http://localhost:8080/swagger-ui/index.html`
