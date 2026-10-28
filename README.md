# Kigali-Motari-Cooperative-Management-System

## Student information

| Field | Value |
| --- | --- |
| **Name** | Iduhorahafi Honorine |
| **Student ID** | 27965 |
| **Project** | Kigali Motari Cooperative Management System |
| **Course** | INSY 8322 – Web Technology and Internet |
| **Repository** | `kigali-motari-cooperative-management-system-backend` |

Backend API for a Kigali moto-taxi cooperative: zones, motorcycles, riders, passengers, rides, and (later) fare receipts.

## Tech stack

- Java 17
- Spring Boot 3.4.x
- Spring Web
- Spring Data JPA
- PostgreSQL
- springdoc-openapi (Swagger UI)

## Prerequisites

1. **JDK 17+** installed (`java -version`)
2. **PostgreSQL** running locally
3. Database created:

```bash
createdb kigali_motari
# or in psql:
# CREATE DATABASE kigali_motari_db;
```

4. Update credentials in `src/main/resources/application.properties` if needed:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/kigali_motari
spring.datasource.username=postgres
spring.datasource.password=honorine
```

## How to run the API

```bash
cd kigali-motari-cooperative-management-system
./mvnw spring-boot:run
```

The API starts at **http://localhost:8081**

### Swagger UI (interactive testing)

Open: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

### Postman

Import the collection at the repo root:

`kigali-motari-cooperative-management-system-midterm.postman_collection.json`

Collection variable `baseUrl` defaults to `http://localhost:8081`.

## Part A endpoints (CRUD)

For `{path}` = `zones` | `motorcycles` | `riders` | `passengers` | `rides`:

| Method | Path | Success |
| --- | --- | --- |
| POST | `/api/{path}/save` | 201 + `"{Entity} saved successfully"` |
| GET | `/api/{path}/all` | 200 + list |
| GET | `/api/{path}/{id}` | 200 + entity, or 404 |
| PUT | `/api/{path}/update/{id}` | 200 + message, or 404 |
| DELETE | `/api/{path}/delete/{id}` | 200 + message, or 404 |

## Seed order (Step 0)

Use Swagger or the Postman **A2** seed requests in this order:

1. Zones → 2. Motorcycles → 3. Riders → 4. Passengers → 5. Rides  

After each parent batch, call **GET /all**, copy UUIDs into the next requests (or run the “capture IDs” GET requests in Postman so variables are filled).

## Project structure

```
src/main/java/com/kigalimotari/cooperative/
  model/        entities + RideStatus
  repository/   Spring Data JPA
  service/      business methods (List / Optional / String)
  controller/   REST endpoints
```
