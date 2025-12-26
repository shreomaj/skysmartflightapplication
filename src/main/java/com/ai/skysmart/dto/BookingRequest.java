package com.ai.skysmart.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class BookingRequest {

    @NotNull(message = "Flight ID is required")
    private Long flightId;  // Use flightId instead of flightNumber

    @NotEmpty(message = "Passenger list cannot be empty")
    private List<PassengerRequest> passengers;

    @NotNull(message = "Payment details are required")
    @Valid
    private PaymentDto payment;

    public Long getFlightId() {
        return flightId;
    }

    public BookingRequest(Long flightId, List<PassengerRequest> passengers, PaymentDto payment) {
        this.flightId = flightId;
        this.passengers = passengers;
        this.payment = payment;
    }
    public PaymentDto getPayment() {
        return payment;
    }
    public void setPayment(PaymentDto payment) {
        this.payment = payment;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public List<PassengerRequest> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerRequest> passengers) {
        this.passengers = passengers;
    }
}
