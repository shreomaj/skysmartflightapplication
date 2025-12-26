package com.ai.skysmart.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

//Tomorrow if you add: dynamic pricing, luggage limits, seat map info
//You update the entity, not the request DTO.  Only when API needs the field — you modify the request DTO.
@Entity
@Table(name="Flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String flightId;
//    This forces Hibernate to generate varchar(255) auto_increment → MYSQL ERROR
    @Column(nullable = false)
    private String flightNumber; // e.g. AI-234


    @Column(nullable = false)
    private String carrier; // e.g. Air India


    @Column(nullable = false)
    private String origin; // IATA or city name


    @Column(nullable = false)
    private String destination;


    @Column(nullable = false)
    private LocalDateTime departureTime;


    @Column(nullable = false)
    private LocalDateTime arrivalTime;


    @Column(nullable = false)
    private Integer totalSeats;


    @Column(nullable = false)
    private Integer availableSeats;


    @Column(nullable = false)
    private Double price; // base price



    @Column(nullable = false)
    private LocalDate journeyDate;
    @Column(nullable = false)
    private Boolean active = true;

    public Flight(Long flightId, String flightNumber, String carrier, String origin, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, Integer totalSeats, Integer availableSeats, Double price, LocalDate  journeyDate, Boolean active) {
        this.flightId = flightId;
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
        this.active = active;
    }

    public LocalDate  getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate  journeyDate) {
        this.journeyDate = journeyDate;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Flight() {
    }
}
