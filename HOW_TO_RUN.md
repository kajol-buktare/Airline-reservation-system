# How to Run

## Start with Maven

```bash
cd airline-reservation-api
mvn spring-boot:run
```

Application runs on **http://localhost:8080**

## Build JAR

```bash
mvn clean package -DskipTests
java -jar target/airline-reservation-api-1.0.0.jar
```

## API Endpoints

| Method | Endpoint | Purpose |
|--------|----------|---------|
| GET | `/api/v1/flights` | Get all flights |
| GET | `/api/v1/flights/{id}` | Get flight by ID |
| POST | `/api/v1/flights` | Create flight |
| PUT | `/api/v1/flights/{id}` | Update flight |
| DELETE | `/api/v1/flights/{id}` | Delete flight |
| GET | `/api/v1/flights/search` | Search flights |

## Tools

- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console

## Stop

Press `Ctrl + C` in terminal
