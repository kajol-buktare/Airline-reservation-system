package com.airline.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standard error response object returned by the API.
 * Provides consistent error information across all endpoints.
 */
public class ErrorResponse {

    private int status;
    private String message;
    private String error;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private String path;
    private Map<String, String> fieldErrors;

    // Constructors
    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message, String error, LocalDateTime timestamp, String path, Map<String, String> fieldErrors) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = timestamp;
        this.path = path;
        this.fieldErrors = fieldErrors;
    }

    // Getters and Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    // Factory method
    public static ErrorResponse of(int status, String message, String error, String path) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(status);
        response.setMessage(message);
        response.setError(error);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(path);
        return response;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int status;
        private String message;
        private String error;
        private LocalDateTime timestamp;
        private String path;
        private Map<String, String> fieldErrors;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder fieldErrors(Map<String, String> fieldErrors) {
            this.fieldErrors = fieldErrors;
            return this;
        }

        public ErrorResponse build() {
            ErrorResponse response = new ErrorResponse();
            response.status = this.status;
            response.message = this.message;
            response.error = this.error;
            response.timestamp = this.timestamp;
            response.path = this.path;
            response.fieldErrors = this.fieldErrors;
            return response;
        }
    }
}

