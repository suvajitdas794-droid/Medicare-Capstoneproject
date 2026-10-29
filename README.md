# Medicare — Healthcare / Medicine Ordering Capstone

A full-stack capstone starter aligned to the Healthcare domain in the supplied Caltech Coding Bootcamp PDF:
- Angular frontend
- Java 17 + Spring Boot REST API
- MySQL
- JWT authentication with ADMIN / USER roles
- Medicine catalog, category/brand/symptom filters, cart, orders and order tracking
- JUnit service tests, Swagger/OpenAPI
- Docker Compose, Jenkins pipeline, AWS EC2 deployment notes

> This is a learning/demo project. It does not process real payments, validate prescriptions clinically, or provide medical advice. Seed/demo data is illustrative only.

## Architecture

- `backend/` — Spring Boot REST API
- `frontend/` — Angular single-page application
- `database/schema.sql` — database schema and seed data
- `docker-compose.yml` — local container stack
- `Jenkinsfile` — build/test/container pipeline
- `deploy/` — EC2 deployment guidance and Nginx config

## Requirements

For local development:
- Java 17+
- Maven 3.9+
- Node.js 20+ and npm
- Angular CLI 18+
- MySQL 8+ (or use Docker Compose)

For container-based run:
- Docker Engine + Docker Compose plugin

## 1. Configure environment

Copy `.env.example` to `.env` and replace the JWT secret with a long random value. Do not commit `.env`.

```bash
cp .env.example .env
```

Windows PowerShell:
```powershell
Copy-Item .env.example .env
```

## 2. Run with Docker Compose

```bash
docker compose up --build
```

URLs:
- Angular: http://localhost:4200
- API: http://localhost:8080/api
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Health: http://localhost:8080/actuator/health

Demo accounts are created by the backend on first start:
- Admin: `admin@medicare.local` / `Admin@12345`
- User: `user@medicare.local` / `User@12345`

Change these credentials before any shared deployment. Demo accounts are for local evaluation only.

## 3. Run backend without Docker

Create a MySQL database, then set environment variables (or edit `backend/src/main/resources/application.yml` for local-only work):

```bash
export DB_URL='jdbc:mysql://localhost:3306/medicare_db?createDatabaseIfNotExist=true'
export DB_USERNAME='root'
export DB_PASSWORD='your-password'
export JWT_SECRET='replace-with-a-long-random-secret-at-least-32-characters'
cd backend
mvn spring-boot:run
```

PowerShell:
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/medicare_db?createDatabaseIfNotExist=true"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your-password"
$env:JWT_SECRET="replace-with-a-long-random-secret-at-least-32-characters"
mvn spring-boot:run
```

## 4. Run Angular without Docker

```bash
cd frontend
npm install
npm start
```

Angular dev server uses `proxy.conf.json` to proxy `/api` to `http://localhost:8080`.

## Features

### Admin
- JWT login and role-based route/API protection
- Dashboard summary
- Create, update and delete medicine products
- Manage medicine categories through the medicine category field
- View all customer orders and update order status

### User
- Register and login
- Browse and search medicines
- Filter by category, brand and symptom
- Add items to cart
- Place a demo order
- View order history and status

### Backend
- REST controllers, services, repositories and JPA entities
- BCrypt password hashing
- JWT bearer authentication
- CORS configuration
- Swagger/OpenAPI docs
- JUnit service tests

## API overview

| Method | Endpoint | Access | Purpose |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Register user |
| POST | `/api/auth/login` | Public | Login and receive JWT |
| GET | `/api/medicines` | Public | Search/filter catalog |
| GET | `/api/medicines/{id}` | Public | Medicine details |
| POST | `/api/medicines` | ADMIN | Create medicine |
| PUT | `/api/medicines/{id}` | ADMIN | Update medicine |
| DELETE | `/api/medicines/{id}` | ADMIN | Delete medicine |
| POST | `/api/orders` | USER | Place order |
| GET | `/api/orders/my` | USER | Current user's order history |
| GET | `/api/orders` | ADMIN | All orders |
| PATCH | `/api/orders/{id}/status` | ADMIN | Update order status |
| GET | `/api/admin/dashboard` | ADMIN | Admin summary |

Swagger UI is available at `/swagger-ui/index.html`.

## Database

Hibernate creates tables from JPA entities with `ddl-auto: update` for convenience in this educational project. `database/schema.sql` is also provided as a reference schema and seed script. For production, use Flyway/Liquibase migrations and set `ddl-auto: validate`.

## Tests

```bash
cd backend
mvn test
```

## Build Docker images manually

```bash
docker build -t medicare-api ./backend
docker build -t medicare-web ./frontend
```

## Jenkins CI/CD

The included `Jenkinsfile` runs Maven tests, builds both images, and can push images when registry credentials are configured. Set Jenkins credentials:
- `docker-registry-credentials` (username/password credential)
- configure `DOCKER_REGISTRY` and `IMAGE_TAG` environment values in the pipeline
- configure a deployment stage or SSH credential for your own EC2 host; do not store secrets in source control.

The pipeline is intentionally safe by default: deployment is gated behind a manual input step and requires Jenkins credentials.

## AWS EC2 deployment outline

1. Create an Ubuntu EC2 instance and restrict inbound traffic to SSH from your IP and HTTP/HTTPS from the public internet.
2. Install Docker Engine and Docker Compose plugin.
3. Copy the project or pull your built images from a registry.
4. Set production environment values in a protected `.env` file on EC2.
5. Use `docker compose up -d --build`.
6. Put Nginx or an AWS Application Load Balancer in front and enable HTTPS with a valid certificate.
7. Never expose MySQL port 3306 publicly.
8. Replace demo passwords, rotate JWT secret, use backups, and configure monitoring before any public demo.

## Important limitations / production hardening

- Payment is a demo order confirmation, not a real payment gateway.
- Prescription-required field is metadata only; the demo does not validate prescription documents.
- Use HTTPS, secrets management, database migrations, rate limiting, audit logging, backups and stronger account lifecycle flows before production use.
