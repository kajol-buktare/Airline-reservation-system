package com.airline.exception;

/**
 * Custom exception thrown when a Flight is not found in the database.
 * Follows REST API best practices for error handling.
 */
public class FlightNotFoundException extends RuntimeException {

    public FlightNotFoundException(String message) {
        super(message);
    }

    public FlightNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlightNotFoundException(Long flightId) {
        super("Flight not found with ID: " + flightId);
    }
}
