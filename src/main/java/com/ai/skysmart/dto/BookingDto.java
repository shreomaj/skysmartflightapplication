package com.ai.skysmart.dto;

import java.time.LocalDateTime;

public class BookingDto {
    private String bookingId;
    private String flightNumber;
    private String passengerName;
    private String email;
    private Integer seatsBooked;
    private LocalDateTime bookingTime;
    private String status;

    public BookingDto(String bookingId, String flightNumber, String passengerName, String email, Integer seatsBooked, LocalDateTime bookingTime, String status) {
        this.bookingId = bookingId;
        this.flightNumber = flightNumber;
        this.passengerName = passengerName;
        this.email = email;
        this.seatsBooked = seatsBooked;
        this.bookingTime = bookingTime;
        this.status = status;
    }

    public BookingDto() {
    }


    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(Integer seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
