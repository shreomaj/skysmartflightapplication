package com.ai.skysmart.repository;

import com.ai.skysmart.entity.Flight;
import com.ai.skysmart.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    //List<Ticket> findByBookingPnr(String pnr);

    List<Ticket> findByBooking_Pnr(String pnr);

    boolean existsByFlightAndSeatNum(Flight flight, Integer newSeat);
}
