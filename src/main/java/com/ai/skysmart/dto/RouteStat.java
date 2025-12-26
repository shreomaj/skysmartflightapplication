package com.ai.skysmart.dto;


public class RouteStat {

    private String origin;
    private String destination;
    private long bookings;

    public RouteStat(String origin, String destination, long bookings) {
        this.origin = origin;
        this.destination = destination;
        this.bookings = bookings;
    }

    public RouteStat() {
    }

    // Getters and Setters
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public long getBookings() { return bookings; }
    public void setBookings(long bookings) { this.bookings = bookings; }


}

