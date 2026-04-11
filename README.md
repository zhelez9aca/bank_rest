# bank_rest

Backend for a simple bank card management system (Spring Boot + PostgreSQL + JWT).

## Features
- User registration/login (JWT in cookie)
- Card creation (PAN generated + encrypted), list cards with pagination/filter
- Transfers between own cards
- Admin: list cards, block/activate/delete cards, deposit
- Admin: list users, block/activate users
- Card block requests (user -> admin approve/reject)
- Flyway migrations
- Swagger UI

## Requirements
- Java 17+
- Maven
- Docker (optional, for PostgreSQL)

## Run PostgreSQL (Docker)
```bash
docker compose up -d
```

Defaults (can be overridden by env):
- DB URL: `jdbc:postgresql://localhost:5432/db`
- DB user: `postgres`
- DB password: `postgres`

## Run App
```bash
mvn spring-boot:run
```

## Env Vars
```bash
DB_URL=jdbc:postgresql://localhost:5432/db
DB_USER=postgres
DB_PASSWORD=postgres
JWT_SECRET=your_jwt_secret_here
PAN_SECRET=your_32_bytes_pan_secret_here
JWT_COOKIE_SECURE=false
```

## Swagger / OpenAPI
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Static spec: `docs/openapi.yaml`

## Tests
```bash
mvn test
```

## Notes
- PAN is generated on the server, encrypted with AES-GCM, and stored as `encrypted_pan` + `last4`.
- Flyway is used for migrations (instead of Liquibase).
