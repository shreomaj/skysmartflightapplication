package com.ai.skysmart.dto;

public class CreateOrderRequest {
    private String bookingId; // matches booking.bookingId (String)

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public CreateOrderRequest(String bookingId) {
        this.bookingId = bookingId;
    }

    public CreateOrderRequest() {
    }
}
