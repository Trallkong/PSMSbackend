# PSMSbackend – Property Management System Backend

Property Management System backend built with Spring Boot 4.0.0, Java 17, and PostgreSQL.

## Tech Stack

- **Spring Boot 4.0.0** / Java 17
- **Spring Data JPA** — ORM with PostgreSQL
- **Spring Security** — Basic Auth, stateless sessions
- **PostgreSQL** — relational database
- **Lombok** — boilerplate reduction
- **Apache Commons Codec** — crypto utilities

## Architecture

```
entity/ → repository/ (JPA) → service/ → controller/ (REST)
```

## Quick Start

### Prerequisites

- JDK 17+ (recommended: JDK 21)
- PostgreSQL running on `localhost:5432`
- Database `PSMS_db` created

### Run

```bash
# Development (port 8080, no SSL)
mvn spring-boot:run -Dspring.profiles.active=local

# Production (port 443, SSL)
mvn spring-boot:run -Dspring.profiles.active=prod

# Default profile (hardcoded dev creds)
mvn spring-boot:run
```

## API Endpoints

All endpoints except `/api/owners` require Basic Auth.

| Prefix | Description |
|---|---|
| `/api/owners` | Owner CRUD (public) |
| `/api/charge_item` | Charge items + auto bill generation on create |
| `/api/bill` | Bills, filter by `/property/{pid}/owner/{oid}[/unpaid]` |
| `/api/payment` | Payments |
| `/api/payment_detail` | Payment details (updates bill amounts) |
| `/api/staff` | Staff login `POST /login` |
| `/api/dashboard` | Aggregates (unpaidSum, paidSum, overdueCount, ownerCount) |
| `/api/crypto` | AES encrypt/decrypt, HMAC sign/verify, key generation |

## Profiles

| Profile | Port  | SSL | Credentials |
|---|---|---|---|
| `local` | 8080 | No | Env var fallback |
| `prod`  | 443  | Yes (keystore.p12) | Env var required |
| default | 8080 | No | Hardcoded dev |

## Testing

```bash
mvn clean test
```
