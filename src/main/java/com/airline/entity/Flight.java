package com.airline.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Flight Entity representing the flight information in the database.
 * Uses JPA annotations for ORM.
 */
@Entity
@Table(name = "flight", indexes = {
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_dep_city", columnList = "dep_city"),
    @Index(name = "idx_arr_city", columnList = "arr_city")
})
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String airline;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false)
    private Double price;

    @Column(name = "dep_city", nullable = false, length = 100)
    private String departureCity;

    @Column(name = "arr_city", nullable = false, length = 100)
    private String arrivalCity;

    @Column(name = "dep_dt", nullable = false)
    private LocalDateTime departureDateTime;

    @Column(name = "arr_dt", nullable = false)
    private LocalDateTime arrivalDateTime;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @Column(name = "img")
    private String imageUrl;

    @Column(nullable = false, length = 100)
    private String email;

    @Version
    private Long version;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Flight() {
    }

    public Flight(String airline, String type, Double price, String departureCity, String arrivalCity,
                  LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, FlightStatus status,
                  String imageUrl, String email) {
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

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
        private FlightStatus status;
        private String imageUrl;
        private String email;
        private Long version;
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

        public Builder status(FlightStatus status) {
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

        public Builder version(Long version) {
            this.version = version;
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

        public Flight build() {
            Flight flight = new Flight();
            flight.id = this.id;
            flight.airline = this.airline;
            flight.type = this.type;
            flight.price = this.price;
            flight.departureCity = this.departureCity;
            flight.arrivalCity = this.arrivalCity;
            flight.departureDateTime = this.departureDateTime;
            flight.arrivalDateTime = this.arrivalDateTime;
            flight.status = this.status;
            flight.imageUrl = this.imageUrl;
            flight.email = this.email;
            flight.version = this.version;
            flight.createdAt = this.createdAt;
            flight.updatedAt = this.updatedAt;
            return flight;
        }
    }

    public enum FlightStatus {
        ACTIVE,
        INACTIVE,
        CANCELLED,
        DELAYED,
        ON_TIME
    }
}
