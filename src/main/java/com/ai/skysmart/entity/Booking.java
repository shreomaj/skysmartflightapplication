package com.ai.skysmart.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
//If your mapper updates the entity — you accidentally give the user control over DB fields.
//Then a hacker can send this in JSON: like price 0, if u use entity not dto in your controller, With DTOs, API stays stable even after database refactoring.use  validations are API-level validations, not DB-level.
//👉 DTO request ensures they can only modify what you allow.
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookingId;

    private String pnr;
    private Double totalAmount;

    private LocalDateTime bookingTime=LocalDateTime.now();
    private String status; // CONFIRMED / CANCELLED

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;   // Needed for showing user history

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Ticket> ticket;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

    public Booking(String bookingId, String pnr, Double totalAmount, LocalDateTime bookingTime, String status, User user, List<Ticket> ticket) {
        this.bookingId = bookingId;
        this.pnr = pnr;
        this.totalAmount = totalAmount;
        this.bookingTime = bookingTime;
        this.status = status;
        this.user = user;
        this.ticket = ticket;
    }


    public Booking() {
    }
}