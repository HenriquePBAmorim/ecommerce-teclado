# E-commerce de Teclados (API REST Quarkus)

Este projeto é uma API REST desenvolvida em **Java/Quarkus**, utilizando **PostgreSQL** para persistência e **Keycloak** para segurança baseada em tokens JWT.

## Pré-requisitos
- **Java 21+**
- **Maven**
- **Docker** e **Docker Compose**

## Como rodar o projeto localmente

Para rodar o projeto sem problemas de conexão, você precisa primeiro subir os containers do Banco de Dados e do Keycloak.

### 1. Subindo a Infraestrutura (Banco e Autenticação)
Na raiz do projeto (onde está o arquivo `docker-compose.yml`), abra o terminal e rode:
```bash
docker compose up -d
```
O Docker irá baixar e iniciar dois containers:
1. **PostgreSQL** (porta `5432`) com o banco `topicos1db`.
2. **Keycloak** (porta `8081`) com o Realm `ecommerce-teclado` importado automaticamente.

### 2. Rodando a Aplicação Quarkus
Com a infraestrutura de pé, você pode rodar a aplicação em modo de desenvolvimento (recomendamos o uso do Maven Wrapper para evitar problemas de versão do Maven):
```bash
# No Linux/Mac:
./mvnw compile quarkus:dev

# No Windows (PowerShell/CMD):
.\mvnw.cmd compile quarkus:dev
```
A API estará disponível em `http://localhost:8080`.

### 3. Endpoints Úteis
- **Swagger UI (Documentação da API):** `http://localhost:8080/q/swagger-ui`
- **Health Checks (Liveness/Readiness):** `http://localhost:8080/q/health`
- **Painel do Keycloak:** `http://localhost:8081` (Usuário: `admin`, Senha: `admin`)

## Observação sobre o Keycloak
As rotas protegidas da API exigem um token Bearer gerado pelo Keycloak. Como o arquivo `realm-export.json` já é importado ao rodar o `docker-compose`, os usuários de teste (ex: `joao`, `maria123`) já devem estar pré-configurados no Realm `ecommerce-teclado`.
