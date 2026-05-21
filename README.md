# CineMiranha


## Alunos
Vitor Taichi Taira 823838
João Paulo Rangel

Aplicativo Android para consulta de filmes em cartaz e sessões de cinema, com backend Spring Boot.

---

## Estrutura do projeto

```
Mobile--CineMiranha/
├── backend/   # API REST — Spring Boot 3 + PostgreSQL
└── Mobile/    # App Android — Jetpack Compose
```

---

## Pré-requisitos

| Ferramenta | Versão mínima |
|---|---|
| Java (JDK) | 17 |
| Maven | 3.9+ (ou usar o `mvnw` incluído) |
| Docker + Docker Compose | qualquer versão recente |
| Android Studio | Hedgehog (2023.1) ou superior |
| Android SDK | API 26+ (compilado com API 35) |

---

## 1. Backend

### 1.1 Subir o banco de dados

Na pasta `backend/`, execute:

```bash
docker compose up -d
```

Isso sobe um PostgreSQL 16 com:
- **Host:** `localhost:5432`
- **Banco:** `cinema-db`
- **Usuário:** `root`
- **Senha:** `root`

### 1.2 Rodar a API

```bash
cd backend
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

Na primeira execução o seeder popula o banco automaticamente com filmes e sessões de exemplo. Nas execuções seguintes o seeder é ignorado.

> **Documentação interativa (Swagger):** `http://localhost:8080/swagger-ui.html`

---

## 2. App Android

### 2.1 Configurar o IP do backend

Abra o arquivo:

```
Mobile/app/src/main/java/br/ufscar/cinemiranha/network/RetrofitClient.kt
```

Altere `BASE_URL` para o IP da máquina que está rodando o backend:

```kotlin
private const val BASE_URL = "http://<SEU_IP>:8080/"
```

> Para descobrir seu IP local, execute `ip addr` (Linux/Mac) ou `ipconfig` (Windows) e use o endereço da interface de rede (ex.: `192.168.x.x`).

Em seguida, adicione esse IP ao arquivo de segurança de rede do app:

```
Mobile/app/src/main/res/xml/network_security_config.xml
```

```xml
<domain includeSubdomains="false">SEU_IP_AQUI</domain>
```

### 2.2 Rodar no Android Studio

1. Abra a pasta `Mobile/` no Android Studio.
2. Aguarde o Gradle sincronizar.
3. Conecte um dispositivo físico via USB (com depuração USB ativada) ou inicie um emulador (API 26+).
4. Clique em **Run** ou pressione `Shift+F10`.

> **Emulador:** use o IP `10.0.2.2` no lugar do IP da máquina — é o alias que o emulador usa para acessar o `localhost` do host.

---

## Resumo rápido

```bash
# 1. Banco de dados
cd backend && docker compose up -d

# 2. API
./mvnw spring-boot:run

# 3. App → abrir Mobile/ no Android Studio e rodar
```
