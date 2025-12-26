package com.ai.skysmart.service;

import com.ai.skysmart.dto.TicketDto;
import com.ai.skysmart.entity.Booking;

import java.util.List;

public interface TicketService {
    List<TicketDto> getTicketsByPnr(String pnr);
    TicketDto getTicketById(Long ticketId);
    //Change Seat
    TicketDto changeSeat(Long ticketId, Integer seatnum);
    //cancelTicket
    void canceTicket(Long ticketId);
    byte[] generateBoardingPass(Long ticketId);

    void generateTicketsForBooking(Booking booking);

    byte[] generateBookingPdf(String bookingId);
}
