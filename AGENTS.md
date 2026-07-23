# PSMSbackend – Property Management System

## Stack
- **Spring Boot 4.0.0** / Java 17 / PostgreSQL
- JPA, Security (Basic Auth), WebMVC
- Lombok, commons-codec

## Architecture
```
entity/ → repository/ (JPA) → service/ → controller/ (REST)
```
- `BaseEntity`: `create_time` / `update_time` via JPA auditing (`@EnableJpaAuditing`)
- All IDs are auto-increment (`GenerationType.IDENTITY`)
- `Fee/` enums: `ChargeType` (0-4) and `CycleType` (0-4) for billing logic

## Known issues
- **GraphQL DGS codegen** is configured in `pom.xml` but no `.graphql*` schema files exist under `src/main/resources/graphql-client` — codegen produces no output (directory created to avoid plugin crash)

## Commands
```bash
mvn spring-boot:run                  # Run with default profile
mvn spring-boot:run -Dspring.profiles.active=local  # Dev (port 8080, no SSL)
mvn spring-boot:run -Dspring.profiles.active=prod   # Prod (port 443, SSL)
mvn clean test                        # Run tests
mvn clean compile                     # Build (includes DGS codegen if schemas exist)
```

## Profiles
- `application-local.yml` — port 8080, no SSL, env-var fallback values
- `application-prod.yml` — port 443, SSL (keystore.p12), env-var required for crypto keys
- `application.yml` — default (hardcoded dev creds)

## Security
- Spring Security Basic Auth, CSRF disabled, STATELESS sessions
- Only `/api/owners` is `permitAll()`, all other endpoints require authentication
- Crypto module: AES-256/GCM + HMAC-SHA256, keys via `crypto.aes.key` / `crypto.hmac.key` config

## API Endpoints
| Prefix | Entity |
|---|---|
| `/api/charge_item` | Charge items + auto bill generation on create |
| `/api/bill` | Bills with `/property/{pid}/owner/{oid}[/unpaid]` |
| `/api/owners` | Owners (permitAll) |
| `/api/payment` | Payments |
| `/api/payment_detail` | Payment details (updates bill amounts) |
| `/api/staff` | Login (`POST /login`) |
| `/api/dashboard` | Aggregates (unpaidSum, paidSum, overdueCount, ownerCount) |
| `/api/crypto` | AES encrypt/decrypt, HMAC sign/verify, key generation |
