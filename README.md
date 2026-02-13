# Airline Reservation API

A Spring Boot REST API for managing airline flights and reservations.

## Tech Stack
- Java 21
- Spring Boot 3.4
- Spring Data JPA
- H2 Database (dev)
- Maven

## Quick Start

```bash
cd airline-reservation-api
mvn spring-boot:run
```

API runs on `http://localhost:8080`

## API Endpoints

- `GET /api/v1/flights` - Get all flights
- `GET /api/v1/flights/{id}` - Get flight by ID
- `POST /api/v1/flights` - Create flight
- `PUT /api/v1/flights/{id}` - Update flight
- `DELETE /api/v1/flights/{id}` - Delete flight
- `GET /api/v1/flights/search` - Search flights

## Tools
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console
- **Postman**: Use `postman_collection.json`

## Requirements
- Java 21+
- Maven 3.9+
