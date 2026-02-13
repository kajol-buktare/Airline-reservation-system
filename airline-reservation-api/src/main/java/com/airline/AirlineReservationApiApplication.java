package com.airline;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for Airline Reservation System REST API.
 * 
 * Features:
 * - Spring Boot 3.4 with Java 21
 * - RESTful API with proper layered architecture
 * - OpenAPI 3.0 (Swagger) documentation
 * - Global exception handling
 * - Data validation with Jakarta Validation
 * - Spring Data JPA with H2/PostgreSQL database
 * 
 * API Documentation: http://localhost:8080/swagger-ui.html
 * OpenAPI JSON: http://localhost:8080/api/v1/api-docs
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Airline Reservation System API",
        version = "1.0.0",
        description = "Professional REST API for managing airline flights and reservations. " +
                      "This API provides comprehensive flight management capabilities with a modern " +
                      "Spring Boot 3.4 architecture, ready for production deployment.",
        termsOfService = "https://example.com/terms",
        contact = @Contact(
            name = "Airline Support",
            url = "https://example.com/support",
            email = "support@airline.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Development Server"),
        @Server(url = "https://api.airline.com", description = "Production Server")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "JWT Bearer token authentication",
    in = SecuritySchemeIn.HEADER
)
public class AirlineReservationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirlineReservationApiApplication.class, args);
    }

}
