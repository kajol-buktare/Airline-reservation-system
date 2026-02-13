package com.airline.service;

import com.airline.dto.FlightDTO;
import com.airline.entity.Flight;
import com.airline.exception.FlightNotFoundException;
import com.airline.repository.FlightRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for FlightService using JUnit 5 and Mockito.
 * Tests cover CRUD operations and business logic.
 * Follows AAA (Arrange-Act-Assert) pattern.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("FlightService Tests")
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    private FlightDTO createTestFlightDTO() {
        return FlightDTO.builder()
            .id(1L)
            .airline("Lufthansa")
            .type("Boeing 737")
            .price(299.99)
            .departureCity("Berlin")
            .arrivalCity("Munich")
            .departureDateTime(LocalDateTime.now().plusDays(7))
            .arrivalDateTime(LocalDateTime.now().plusDays(7).plusHours(2))
            .status("ACTIVE")
            .imageUrl("https://example.com/plane.jpg")
            .email("admin@airline.com")
            .build();
    }

    private Flight createTestFlight() {
        return Flight.builder()
            .id(1L)
            .airline("Lufthansa")
            .type("Boeing 737")
            .price(299.99)
            .departureCity("Berlin")
            .arrivalCity("Munich")
            .departureDateTime(LocalDateTime.now().plusDays(7))
            .arrivalDateTime(LocalDateTime.now().plusDays(7).plusHours(2))
            .status(Flight.FlightStatus.ACTIVE)
            .imageUrl("https://example.com/plane.jpg")
            .email("admin@airline.com")
            .build();
    }

    // ==================== GET Tests ====================

    @Test
    @DisplayName("Should retrieve all flights successfully")
    void testGetAllFlights() {
        // Arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(createTestFlight());
        flightList.add(createTestFlight());

        when(flightRepository.findAll()).thenReturn(flightList);

        // Act
        List<FlightDTO> result = flightService.getAllFlights();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Lufthansa", result.get(0).getAirline());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve flight by ID successfully")
    void testGetFlightById_Success() {
        // Arrange
        Flight flight = createTestFlight();
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        // Act
        FlightDTO result = flightService.getFlightById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Lufthansa", result.getAirline());
        assertEquals(1L, result.getId());
        verify(flightRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw FlightNotFoundException when flight ID not found")
    void testGetFlightById_NotFound() {
        // Arrange
        when(flightRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FlightNotFoundException.class, () -> flightService.getFlightById(999L));
        verify(flightRepository, times(1)).findById(999L);
    }

    // ==================== CREATE Tests ====================

    @Test
    @DisplayName("Should create a new flight successfully")
    void testCreateFlight_Success() {
        // Arrange
        FlightDTO flightDTO = createTestFlightDTO();
        flightDTO.setId(null); // New flight should not have ID
        Flight flight = createTestFlight();
        flight.setId(1L);

        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        // Act
        FlightDTO result = flightService.createFlight(flightDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Lufthansa", result.getAirline());
        assertEquals(1L, result.getId());
        assertEquals("ACTIVE", result.getStatus());
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    @DisplayName("Should set default status to ACTIVE when creating flight")
    void testCreateFlight_DefaultStatus() {
        // Arrange
        FlightDTO flightDTO = createTestFlightDTO();
        flightDTO.setStatus(null);
        Flight flight = createTestFlight();

        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        // Act
        FlightDTO result = flightService.createFlight(flightDTO);

        // Assert
        assertEquals("ACTIVE", result.getStatus());
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    // ==================== UPDATE Tests ====================

    @Test
    @DisplayName("Should update flight successfully")
    void testUpdateFlight_Success() {
        // Arrange
        Long flightId = 1L;
        FlightDTO updateDTO = createTestFlightDTO();
        updateDTO.setAirline("Lufthansa Premium");
        updateDTO.setPrice(399.99);

        Flight existingFlight = createTestFlight();
        Flight updatedFlight = createTestFlight();
        updatedFlight.setAirline("Lufthansa Premium");
        updatedFlight.setPrice(399.99);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));
        when(flightRepository.save(any(Flight.class))).thenReturn(updatedFlight);

        // Act
        FlightDTO result = flightService.updateFlight(flightId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Lufthansa Premium", result.getAirline());
        assertEquals(399.99, result.getPrice());
        verify(flightRepository, times(1)).findById(flightId);
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    @DisplayName("Should throw FlightNotFoundException when updating non-existent flight")
    void testUpdateFlight_NotFound() {
        // Arrange
        FlightDTO updateDTO = createTestFlightDTO();
        when(flightRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FlightNotFoundException.class, () -> flightService.updateFlight(999L, updateDTO));
        verify(flightRepository, times(1)).findById(999L);
        verify(flightRepository, never()).save(any(Flight.class));
    }

    // ==================== DELETE Tests ====================

    @Test
    @DisplayName("Should delete flight successfully")
    void testDeleteFlight_Success() {
        // Arrange
        Long flightId = 1L;
        Flight flight = createTestFlight();
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        doNothing().when(flightRepository).delete(any(Flight.class));

        // Act
        flightService.deleteFlight(flightId);

        // Assert
        verify(flightRepository, times(1)).findById(flightId);
        verify(flightRepository, times(1)).delete(any(Flight.class));
    }

    @Test
    @DisplayName("Should throw FlightNotFoundException when deleting non-existent flight")
    void testDeleteFlight_NotFound() {
        // Arrange
        when(flightRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FlightNotFoundException.class, () -> flightService.deleteFlight(999L));
        verify(flightRepository, times(1)).findById(999L);
        verify(flightRepository, never()).delete(any(Flight.class));
    }

    // ==================== SEARCH Tests ====================

    @Test
    @DisplayName("Should search flights with all criteria successfully")
    void testSearchFlights_AllCriteria() {
        // Arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(createTestFlight());

        when(flightRepository.searchFlights("Berlin", "Munich", Flight.FlightStatus.ACTIVE))
            .thenReturn(flightList);

        // Act
        List<FlightDTO> result = flightService.searchFlights("Berlin", "Munich", "ACTIVE");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(flightRepository, times(1)).searchFlights("Berlin", "Munich", Flight.FlightStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should search flights with partial criteria")
    void testSearchFlights_PartialCriteria() {
        // Arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(createTestFlight());

        when(flightRepository.searchFlights("Berlin", null, null))
            .thenReturn(flightList);

        // Act
        List<FlightDTO> result = flightService.searchFlights("Berlin", null, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(flightRepository, times(1)).searchFlights("Berlin", null, null);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for invalid status")
    void testSearchFlights_InvalidStatus() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> flightService.searchFlights("Berlin", "Munich", "INVALID_STATUS"));
    }

    // ==================== FILTER Tests ====================

    @Test
    @DisplayName("Should retrieve flights by departure city")
    void testGetFlightsByDepartureCity() {
        // Arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(createTestFlight());

        when(flightRepository.findByDepartureCity("Berlin")).thenReturn(flightList);

        // Act
        List<FlightDTO> result = flightService.getFlightsByDepartureCity("Berlin");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Berlin", result.get(0).getDepartureCity());
        verify(flightRepository, times(1)).findByDepartureCity("Berlin");
    }

    @Test
    @DisplayName("Should retrieve flights by arrival city")
    void testGetFlightsByArrivalCity() {
        // Arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(createTestFlight());

        when(flightRepository.findByArrivalCity("Munich")).thenReturn(flightList);

        // Act
        List<FlightDTO> result = flightService.getFlightsByArrivalCity("Munich");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Munich", result.get(0).getArrivalCity());
        verify(flightRepository, times(1)).findByArrivalCity("Munich");
    }

    @Test
    @DisplayName("Should retrieve flights by status")
    void testGetFlightsByStatus() {
        // Arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(createTestFlight());

        when(flightRepository.findByStatus(Flight.FlightStatus.ACTIVE)).thenReturn(flightList);

        // Act
        List<FlightDTO> result = flightService.getFlightsByStatus("ACTIVE");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ACTIVE", result.get(0).getStatus());
        verify(flightRepository, times(1)).findByStatus(Flight.FlightStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for invalid status in getFlightsByStatus")
    void testGetFlightsByStatus_InvalidStatus() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> flightService.getFlightsByStatus("INVALID"));
    }

    @Test
    @DisplayName("Should retrieve flights by airline")
    void testGetFlightsByAirline() {
        // Arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(createTestFlight());

        when(flightRepository.findByAirlineContainingIgnoreCase("Lufthansa"))
            .thenReturn(flightList);

        // Act
        List<FlightDTO> result = flightService.getFlightsByAirline("Lufthansa");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Lufthansa", result.get(0).getAirline());
        verify(flightRepository, times(1)).findByAirlineContainingIgnoreCase("Lufthansa");
    }

    @Test
    @DisplayName("Should retrieve flights departing after specific date")
    void testGetFlightsDepartingAfter() {
        // Arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(createTestFlight());
        LocalDateTime departureDateTime = LocalDateTime.now().plusDays(5);

        when(flightRepository.findFlightsDepartingAfter(departureDateTime)).thenReturn(flightList);

        // Act
        List<FlightDTO> result = flightService.getFlightsDepartingAfter(departureDateTime);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(flightRepository, times(1)).findFlightsDepartingAfter(departureDateTime);
    }

    // ==================== EDGE CASES ====================

    @Test
    @DisplayName("Should return empty list when no flights exist")
    void testGetAllFlights_Empty() {
        // Arrange
        when(flightRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<FlightDTO> result = flightService.getAllFlights();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should handle null values in search criteria")
    void testSearchFlights_AllNull() {
        // Arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(createTestFlight());

        when(flightRepository.searchFlights(null, null, null))
            .thenReturn(flightList);

        // Act
        List<FlightDTO> result = flightService.searchFlights(null, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(flightRepository, times(1)).searchFlights(null, null, null);
    }
}
