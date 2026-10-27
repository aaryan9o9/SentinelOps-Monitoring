# SentinelOps Backend — Person 1 Module

Java 17 + Spring Boot + PostgreSQL backend for the SentinelOps project.

## What is included

- Incident create/list/detail/status APIs
- Filtering incidents by status and severity
- Monitored-service create/list/health-status APIs
- Dashboard summary API
- PostgreSQL persistence through Spring Data JPA
- Request validation and consistent API errors
- Spring Boot Actuator health/metrics endpoints
- Docker Compose file for PostgreSQL

> This is the backend foundation for Person 1. It does not yet include authentication, the Python AI service, Prometheus scraping configuration, or automated recovery. Those are follow-up integrations for the team.

## Requirements

- Java 17+
- Maven 3.9+ (or use the Maven wrapper if you add one)
- Docker Desktop, or a local PostgreSQL 16+ instance
- IntelliJ IDEA or another Java IDE
- Postman for API testing

## Run PostgreSQL

From the project directory:

```bash
docker compose up -d
```

The local database is:
- Database: `sentinelops`
- Username: `postgres`
- Password: `postgres`
- Port: `5432`

These are development credentials only. Change them before deploying anywhere shared or public.

## Run the backend

From the project directory:

```bash
mvn spring-boot:run
```

The API runs at `http://localhost:8080`.

You can also run `SentinelOpsApplication.java` from IntelliJ.

## Main endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/services` | Register a monitored service |
| GET | `/api/services` | List monitored services |
| PATCH | `/api/services/{id}/health` | Update health state |
| POST | `/api/incidents` | Create an incident |
| GET | `/api/incidents` | List incidents |
| GET | `/api/incidents?status=OPEN` | Filter by status |
| GET | `/api/incidents?severity=HIGH` | Filter by severity |
| GET | `/api/incidents/{id}` | Get one incident |
| PATCH | `/api/incidents/{id}/status` | Change incident status |
| GET | `/api/dashboard/summary` | Dashboard counters |
| GET | `/actuator/health` | Backend health |
| GET | `/actuator/metrics` | Actuator metrics index |

Status values: `OPEN`, `INVESTIGATING`, `RESOLVED`, `IGNORED`

Severity values: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`

Service health values: `UP`, `DEGRADED`, `DOWN`, `UNKNOWN`

## Quick Postman walkthrough

### 1. Register a monitored service

`POST http://localhost:8080/api/services`

Header: `Content-Type: application/json`

```json
{
  "name": "E-commerce API",
  "description": "Demo Spring Boot service monitored by SentinelOps"
}
```

Copy the returned service `id`.

### 2. Create an incident

`POST http://localhost:8080/api/incidents`

```json
{
  "title": "High HTTP 500 error rate",
  "description": "The monitored service returned an unusually high number of server errors.",
  "severity": "HIGH",
  "source": "rule-engine",
  "evidence": "HTTP 500 count exceeded the configured threshold for the observation window.",
  "serviceId": 1
}
```

Replace `serviceId` with the ID returned from step 1.

### 3. List incidents

`GET http://localhost:8080/api/incidents`

### 4. Filter incidents

`GET http://localhost:8080/api/incidents?status=OPEN&severity=HIGH`

### 5. Update incident status

`PATCH http://localhost:8080/api/incidents/1/status`

```json
{
  "status": "INVESTIGATING"
}
```

### 6. View dashboard summary

`GET http://localhost:8080/api/dashboard/summary`

### 7. Update service health

`PATCH http://localhost:8080/api/services/1/health`

```json
{
  "healthStatus": "DEGRADED"
}
```

## How Person 1's module connects to the other members

- **Person 2 (AI/ML):** Agree on a request/response contract for incident analysis. The backend can later store AI analysis and recommendations on an incident.
- **Person 3 (Monitoring):** Agree on the event format used when a threshold is breached. This backend currently accepts an incident through `POST /api/incidents`; a secure service-to-service ingestion mechanism can be added.
- **Person 4 (Frontend):** Use the REST endpoints above to display services, incidents, status, and dashboard summary.

## Suggested next steps

1. Run the backend and test the endpoints in Postman.
2. Add integration tests for create/list/update flows.
3. Add pagination to incident listing if needed.
4. Add authentication and role-based access before exposing the API outside localhost.
5. Add service-to-service authentication for the monitoring and AI components.
6. Use Flyway or Liquibase migrations before treating the database schema as stable.
7. Replace `ddl-auto=update` with a migration-based schema strategy before production deployment.

## Security note

This is a student-project starter, not a production-hardened service. It intentionally has no authentication configured yet. Keep it on localhost or a trusted development network until authentication, authorization, secrets management, and request protections are implemented.
