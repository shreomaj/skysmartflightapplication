package com.ai.skysmart.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateFlightRequest {
    @NotBlank
    private String flightNumber;


    @NotBlank
    private String carrier;


    @NotBlank
    private String origin;


    @NotBlank
    private String destination;


    @NotNull
    private LocalDateTime departureTime;


    @NotNull
    private LocalDateTime arrivalTime;


    @NotNull
    @Min(1)
    private Integer totalSeats;


    @NotNull
    @Min(0)
    private Integer availableSeats;


    @NotNull
    @PositiveOrZero
    private Double price;

    @Column(nullable = false)
    private LocalDate journeyDate;

    public CreateFlightRequest(String flightNumber, String carrier, String origin, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, Integer totalSeats, Integer availableSeats, Double price, LocalDate journeyDate) {
        this.flightNumber = flightNumber;
        this.carrier = carrier;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.price = price;
        this.journeyDate = journeyDate;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }
    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }


    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
