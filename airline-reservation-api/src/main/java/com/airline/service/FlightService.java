package com.airline.service;

import com.airline.dto.FlightDTO;
import com.airline.entity.Flight;
import com.airline.exception.FlightNotFoundException;
import com.airline.repository.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlightService {

    private static final Logger log = LoggerFactory.getLogger(FlightService.class);
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Transactional(readOnly = true)
    public List<FlightDTO> getAllFlights() {
        log.info("Fetching all flights");
        return flightRepository.findAll().stream()
            .map(FlightDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FlightDTO getFlightById(Long flightId) {
        log.info("Fetching flight with ID: {}", flightId);
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new FlightNotFoundException(flightId));
        return FlightDTO.fromEntity(flight);
    }

    public FlightDTO createFlight(FlightDTO flightDTO) {
        log.info("Creating new flight: {} - {} to {}", 
            flightDTO.getAirline(), 
            flightDTO.getDepartureCity(), 
            flightDTO.getArrivalCity());

        Flight flight = flightDTO.toEntity();
        if (flight.getStatus() == null) {
            flight.setStatus(Flight.FlightStatus.ACTIVE);
        }
        Flight savedFlight = flightRepository.save(flight);
        return FlightDTO.fromEntity(savedFlight);
    }

    public FlightDTO updateFlight(Long flightId, FlightDTO flightDTO) {
        log.info("Updating flight with ID: {}", flightId);

        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new FlightNotFoundException(flightId));

        flight.setAirline(flightDTO.getAirline());
        flight.setType(flightDTO.getType());
        flight.setPrice(flightDTO.getPrice());
        flight.setDepartureCity(flightDTO.getDepartureCity());
        flight.setArrivalCity(flightDTO.getArrivalCity());
        flight.setDepartureDateTime(flightDTO.getDepartureDateTime());
        flight.setArrivalDateTime(flightDTO.getArrivalDateTime());
        flight.setStatus(Flight.FlightStatus.valueOf(flightDTO.getStatus()));
        flight.setImageUrl(flightDTO.getImageUrl());
        flight.setEmail(flightDTO.getEmail());

        Flight updatedFlight = flightRepository.save(flight);
        return FlightDTO.fromEntity(updatedFlight);
    }

    public void deleteFlight(Long flightId) {
        log.info("Deleting flight with ID: {}", flightId);

        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new FlightNotFoundException(flightId));

        flightRepository.delete(flight);
        log.info("Flight deleted successfully: {}", flightId);
    }

    @Transactional(readOnly = true)
    public List<FlightDTO> searchFlights(String departureCity, String arrivalCity, String status) {
        log.info("Searching flights with criteria - Departure: {}, Arrival: {}, Status: {}", 
            departureCity, arrivalCity, status);

        Flight.FlightStatus flightStatus = null;
        if (status != null && !status.isEmpty()) {
            try {
                flightStatus = Flight.FlightStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("Invalid flight status provided: {}", status);
                throw new IllegalArgumentException("Invalid flight status: " + status);
            }
        }

        List<Flight> flights = flightRepository.searchFlights(departureCity, arrivalCity, flightStatus);
        return flights.stream()
            .map(FlightDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FlightDTO> getFlightsByDepartureCity(String departureCity) {
        log.info("Fetching flights from: {}", departureCity);
        return flightRepository.findByDepartureCity(departureCity).stream()
            .map(FlightDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FlightDTO> getFlightsByArrivalCity(String arrivalCity) {
        log.info("Fetching flights to: {}", arrivalCity);
        return flightRepository.findByArrivalCity(arrivalCity).stream()
            .map(FlightDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FlightDTO> getFlightsByStatus(String status) {
        log.info("Fetching flights with status: {}", status);
        try {
            Flight.FlightStatus flightStatus = Flight.FlightStatus.valueOf(status.toUpperCase());
            return flightRepository.findByStatus(flightStatus).stream()
                .map(FlightDTO::fromEntity)
                .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid flight status provided: {}", status);
            throw new IllegalArgumentException("Invalid flight status: " + status);
        }
    }

    @Transactional(readOnly = true)
    public List<FlightDTO> getFlightsDepartingAfter(LocalDateTime departureDateTime) {
        log.info("Fetching flights departing after: {}", departureDateTime);
        return flightRepository.findFlightsDepartingAfter(departureDateTime).stream()
            .map(FlightDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FlightDTO> getFlightsByAirline(String airline) {
        log.info("Fetching flights by airline: {}", airline);
        return flightRepository.findByAirlineContainingIgnoreCase(airline).stream()
            .map(FlightDTO::fromEntity)
            .collect(Collectors.toList());
    }
}
