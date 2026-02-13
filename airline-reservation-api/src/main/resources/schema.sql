-- Database initialization script for H2 (Development)
-- This script will be executed automatically on application startup

DROP TABLE IF EXISTS flight;

CREATE TABLE flight (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    airline VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    dep_city VARCHAR(100) NOT NULL,
    arr_city VARCHAR(100) NOT NULL,
    dep_dt TIMESTAMP NOT NULL,
    arr_dt TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    img VARCHAR(255),
    email VARCHAR(100) NOT NULL,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Create indexes for better query performance
CREATE INDEX idx_status ON flight(status);
CREATE INDEX idx_dep_city ON flight(dep_city);
CREATE INDEX idx_arr_city ON flight(arr_city);
CREATE INDEX idx_airline ON flight(airline);

-- Insert sample data for development
INSERT INTO flight (airline, type, price, dep_city, arr_city, dep_dt, arr_dt, status, img, email) VALUES
('Lufthansa', 'Boeing 737', 299.99, 'Berlin', 'Munich', CURRENT_TIMESTAMP + INTERVAL '7' DAY, CURRENT_TIMESTAMP + INTERVAL '7' DAY + INTERVAL '2' HOUR, 'ACTIVE', 'https://example.com/lufthansa.jpg', 'admin@lufthansa.com'),
('Lufthansa', 'Airbus A320', 199.99, 'Berlin', 'Frankfurt', CURRENT_TIMESTAMP + INTERVAL '5' DAY, CURRENT_TIMESTAMP + INTERVAL '5' DAY + INTERVAL '90' MINUTE, 'ACTIVE', 'https://example.com/airbus.jpg', 'admin@lufthansa.com'),
('Ryanair', 'Boeing 737', 89.99, 'Berlin', 'Rome', CURRENT_TIMESTAMP + INTERVAL '10' DAY, CURRENT_TIMESTAMP + INTERVAL '10' DAY + INTERVAL '3' HOUR, 'ACTIVE', 'https://example.com/ryanair.jpg', 'admin@ryanair.com'),
('KLM', 'Airbus A350', 349.99, 'Amsterdam', 'New York', CURRENT_TIMESTAMP + INTERVAL '14' DAY, CURRENT_TIMESTAMP + INTERVAL '14' DAY + INTERVAL '8' HOUR, 'ACTIVE', 'https://example.com/klm.jpg', 'admin@klm.com'),
('Lufthansa', 'Boeing 777', 599.99, 'Munich', 'Shanghai', CURRENT_TIMESTAMP + INTERVAL '20' DAY, CURRENT_TIMESTAMP + INTERVAL '20' DAY + INTERVAL '12' HOUR, 'DELAYED', 'https://example.com/boeing777.jpg', 'admin@lufthansa.com');
