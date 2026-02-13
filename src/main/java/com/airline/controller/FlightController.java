package com.airline.controller;

import com.airline.dto.FlightDTO;
import com.airline.service.FlightService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/flights")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FlightController {

    private static final Logger log = LoggerFactory.getLogger(FlightController.class);
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        log.info("Request received: GET /api/v1/flights");
        List<FlightDTO> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id) {
        log.info("Request received: GET /api/v1/flights/{}", id);
        FlightDTO flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createFlight(@Valid @RequestBody FlightDTO flightDTO) {
        log.info("Request received: POST /api/v1/flights - Airline: {}", flightDTO.getAirline());
        FlightDTO createdFlight = flightService.createFlight(flightDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Flight created successfully");
        response.put("data", createdFlight);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateFlight(
        @PathVariable Long id,
        @Valid @RequestBody FlightDTO flightDTO) {
        log.info("Request received: PUT /api/v1/flights/{}", id);
        FlightDTO updatedFlight = flightService.updateFlight(id, flightDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Flight updated successfully");
        response.put("data", updatedFlight);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteFlight(@PathVariable Long id) {
        log.info("Request received: DELETE /api/v1/flights/{}", id);
        flightService.deleteFlight(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Flight deleted successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightDTO>> searchFlights(
        @RequestParam(value = "departure_city", required = false) String departureCity,
        @RequestParam(value = "arrival_city", required = false) String arrivalCity,
        @RequestParam(value = "status", required = false) String status) {
        log.info("Request received: GET /api/v1/flights/search - Departure: {}, Arrival: {}, Status: {}",
            departureCity, arrivalCity, status);
        List<FlightDTO> flights = flightService.searchFlights(departureCity, arrivalCity, status);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/departure-city/{city}")
    public ResponseEntity<List<FlightDTO>> getFlightsByDepartureCity(@PathVariable String city) {
        log.info("Request received: GET /api/v1/flights/departure-city/{}", city);
        List<FlightDTO> flights = flightService.getFlightsByDepartureCity(city);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/arrival-city/{city}")
    public ResponseEntity<List<FlightDTO>> getFlightsByArrivalCity(@PathVariable String city) {
        log.info("Request received: GET /api/v1/flights/arrival-city/{}", city);
        List<FlightDTO> flights = flightService.getFlightsByArrivalCity(city);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FlightDTO>> getFlightsByStatus(@PathVariable String status) {
        log.info("Request received: GET /api/v1/flights/status/{}", status);
        List<FlightDTO> flights = flightService.getFlightsByStatus(status);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/departing-after/{dateTime}")
    public ResponseEntity<List<FlightDTO>> getFlightsDepartingAfter(@PathVariable String dateTime) {
        log.info("Request received: GET /api/v1/flights/departing-after/{}", dateTime);
        LocalDateTime departureDateTime = LocalDateTime.parse(dateTime);
        List<FlightDTO> flights = flightService.getFlightsDepartingAfter(departureDateTime);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/airline/{name}")
    public ResponseEntity<List<FlightDTO>> getFlightsByAirline(@PathVariable String name) {
        log.info("Request received: GET /api/v1/flights/airline/{}", name);
        List<FlightDTO> flights = flightService.getFlightsByAirline(name);
        return ResponseEntity.ok(flights);
    }
}
