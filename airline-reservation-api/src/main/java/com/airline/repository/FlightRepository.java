package com.airline.repository;

import com.airline.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Flight entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    /**
     * Find all flights by departure city.
     */
    List<Flight> findByDepartureCity(String departureCity);

    /**
     * Find all flights by arrival city.
     */
    List<Flight> findByArrivalCity(String arrivalCity);

    /**
     * Find flights by status.
     */
    List<Flight> findByStatus(Flight.FlightStatus status);

    /**
     * Find flights by departure city, arrival city, and status.
     */
    List<Flight> findByDepartureCityAndArrivalCityAndStatus(
        String departureCity,
        String arrivalCity,
        Flight.FlightStatus status
    );

    /**
     * Custom query to find flights departing after a specific date.
     */
    @Query("SELECT f FROM Flight f WHERE f.departureDateTime >= :departureDateTime ORDER BY f.departureDateTime ASC")
    List<Flight> findFlightsDepartingAfter(@Param("departureDateTime") LocalDateTime departureDateTime);

    /**
     * Custom query to search flights with flexible criteria.
     */
    @Query("SELECT f FROM Flight f WHERE " +
           "(:departureCity IS NULL OR LOWER(f.departureCity) LIKE LOWER(CONCAT('%', :departureCity, '%'))) AND " +
           "(:arrivalCity IS NULL OR LOWER(f.arrivalCity) LIKE LOWER(CONCAT('%', :arrivalCity, '%'))) AND " +
           "(:status IS NULL OR f.status = :status) " +
           "ORDER BY f.departureDateTime ASC")
    List<Flight> searchFlights(
        @Param("departureCity") String departureCity,
        @Param("arrivalCity") String arrivalCity,
        @Param("status") Flight.FlightStatus status
    );

    /**
     * Find flights by airline name.
     */
    List<Flight> findByAirlineContainingIgnoreCase(String airline);

    /**
     * Check if a flight exists by departure and arrival cities.
     */
    boolean existsByDepartureCityAndArrivalCity(String departureCity, String arrivalCity);
}
