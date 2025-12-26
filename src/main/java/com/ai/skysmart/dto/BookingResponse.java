package com.ai.skysmart.dto;

import java.util.List;

public class BookingResponse {
    private String bookingId;
    private String pnr;
    private Double totalAmount;
    private  String status;
    private List<TicketDto> tickets;

    public BookingResponse(String bookingId, String pnr, Double totalAmount,String status, List<TicketDto> tickets) {
        this.bookingId = bookingId;
        this.pnr = pnr;
        this.totalAmount = totalAmount;
        this.status=status;
        this.tickets = tickets;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<TicketDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketDto> tickets) {
        this.tickets = tickets;
    }

    public BookingResponse() {
    }
}
