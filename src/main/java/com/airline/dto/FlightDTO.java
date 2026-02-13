package com.airline.dto;

import com.airline.entity.Flight;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Flight entity.
 * Used to expose only necessary fields to API clients.
 * Includes Jakarta Validation annotations for input validation.
 */
public class FlightDTO {

    @JsonProperty("id")
    private Long id;

    @NotBlank(message = "Airline name cannot be blank")
    @Size(min = 2, max = 100, message = "Airline name must be between 2 and 100 characters")
    private String airline;

    @NotBlank(message = "Flight type cannot be blank")
    @Size(min = 1, max = 50, message = "Flight type must be between 1 and 50 characters")
    private String type;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @DecimalMax(value = "999999.99", message = "Price cannot exceed 999999.99")
    private Double price;

    @NotBlank(message = "Departure city cannot be blank")
    @Size(min = 2, max = 100, message = "Departure city must be between 2 and 100 characters")
    @JsonProperty("departure_city")
    private String departureCity;

    @NotBlank(message = "Arrival city cannot be blank")
    @Size(min = 2, max = 100, message = "Arrival city must be between 2 and 100 characters")
    @JsonProperty("arrival_city")
    private String arrivalCity;

    @NotNull(message = "Departure date/time cannot be null")
    @Future(message = "Departure date/time must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("departure_datetime")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date/time cannot be null")
    @Future(message = "Arrival date/time must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("arrival_datetime")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Status cannot be null")
    private String status;

    @JsonProperty("image_url")
    private String imageUrl;

    @NotBlank(message = "Admin email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    // Constructors
    public FlightDTO() {
    }

    public FlightDTO(Long id, String airline, String type, Double price, String departureCity,
                     String arrivalCity, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime,
                     String status, String imageUrl, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.airline = airline;
        this.type = type;
        this.price = price;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.status = status;
        this.imageUrl = imageUrl;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Convert FlightDTO to Flight entity.
     */
    public Flight toEntity() {
        return Flight.builder()
            .airline(this.airline)
            .type(this.type)
            .price(this.price)
            .departureCity(this.departureCity)
            .arrivalCity(this.arrivalCity)
            .departureDateTime(this.departureDateTime)
            .arrivalDateTime(this.arrivalDateTime)
            .status(Flight.FlightStatus.valueOf(this.status))
            .imageUrl(this.imageUrl)
            .email(this.email)
            .build();
    }

    /**
     * Convert Flight entity to FlightDTO.
     */
    public static FlightDTO fromEntity(Flight flight) {
        FlightDTO dto = new FlightDTO();
        dto.setId(flight.getId());
        dto.setAirline(flight.getAirline());
        dto.setType(flight.getType());
        dto.setPrice(flight.getPrice());
        dto.setDepartureCity(flight.getDepartureCity());
        dto.setArrivalCity(flight.getArrivalCity());
        dto.setDepartureDateTime(flight.getDepartureDateTime());
        dto.setArrivalDateTime(flight.getArrivalDateTime());
        dto.setStatus(flight.getStatus().name());
        dto.setImageUrl(flight.getImageUrl());
        dto.setEmail(flight.getEmail());
        dto.setCreatedAt(flight.getCreatedAt());
        dto.setUpdatedAt(flight.getUpdatedAt());
        return dto;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String airline;
        private String type;
        private Double price;
        private String departureCity;
        private String arrivalCity;
        private LocalDateTime departureDateTime;
        private LocalDateTime arrivalDateTime;
        private String status;
        private String imageUrl;
        private String email;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder airline(String airline) {
            this.airline = airline;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder departureCity(String departureCity) {
            this.departureCity = departureCity;
            return this;
        }

        public Builder arrivalCity(String arrivalCity) {
            this.arrivalCity = arrivalCity;
            return this;
        }

        public Builder departureDateTime(LocalDateTime departureDateTime) {
            this.departureDateTime = departureDateTime;
            return this;
        }

        public Builder arrivalDateTime(LocalDateTime arrivalDateTime) {
            this.arrivalDateTime = arrivalDateTime;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public FlightDTO build() {
            FlightDTO dto = new FlightDTO();
            dto.id = this.id;
            dto.airline = this.airline;
            dto.type = this.type;
            dto.price = this.price;
            dto.departureCity = this.departureCity;
            dto.arrivalCity = this.arrivalCity;
            dto.departureDateTime = this.departureDateTime;
            dto.arrivalDateTime = this.arrivalDateTime;
            dto.status = this.status;
            dto.imageUrl = this.imageUrl;
            dto.email = this.email;
            dto.createdAt = this.createdAt;
            dto.updatedAt = this.updatedAt;
            return dto;
        }
    }
}
