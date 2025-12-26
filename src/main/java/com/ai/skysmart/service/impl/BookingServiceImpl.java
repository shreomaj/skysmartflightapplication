package com.ai.skysmart.service.impl;

import com.ai.skysmart.dto.*;
import com.ai.skysmart.entity.Booking;
import com.ai.skysmart.entity.Flight;
import com.ai.skysmart.entity.Ticket;
import com.ai.skysmart.entity.User;
import com.ai.skysmart.repository.BookingRepository;
import com.ai.skysmart.repository.FlightRepository;
import com.ai.skysmart.repository.UserRepository;
import com.ai.skysmart.service.BookingService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepo;

    @Override
    @Transactional
    public BookingResponse create(Long userId, BookingRequest request) {

        // Validate user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lock flight for booking
        Flight flight = flightRepository.lockFlightForBooking(request.getFlightId());

        if (flight == null) {
            throw new RuntimeException("Flight not found");
        }

        // Validate flight active
        if (!flight.getActive()) {
            throw new RuntimeException("Flight is not active for booking");
        }

        // Validate seats
        int requestedSeats = request.getPassengers().size();
        int availableSeats = flight.getAvailableSeats();

        if (availableSeats < requestedSeats) {
            throw new RuntimeException("Not enough seats available!");
        }

        // Assign seats based on old state
        int seatCounter = (flight.getTotalSeats() - availableSeats) + 1;

        // Deduct seats
        flight.setAvailableSeats(availableSeats - requestedSeats);
        flightRepository.save(flight);

        // Create booking
        Booking booking = new Booking();
        booking.setPnr("PNR" + UUID.randomUUID().toString().substring(0, 6));
        booking.setStatus("CONFIRMED");
        booking.setUser(user);
        double totalAmount=0;
        List<Ticket> tickets = new ArrayList<>();

        for (PassengerRequest passenger : request.getPassengers()) {
            Ticket tick = new Ticket();
            tick.setPassengerName(passenger.getName());
            tick.setAge(passenger.getAge());
            tick.setGender(passenger.getGender());
            tick.setSeatNum(seatCounter++);
            tick.setPrice(flight.getPrice());
            tick.setFlight(flight);
            tick.setBooking(booking);
            tick.setPrice(flight.getPrice());
            tick.setMealPreference("VEG");


            totalAmount+= tick.getPrice();
            tickets.add(tick);
        }

        booking.setTicket(tickets);
        booking.setTotalAmount(tickets.stream().mapToDouble(Ticket::getPrice).sum());

        Booking saved = bookingRepository.save(booking);

        // Convert to DTO
        BookingResponse response = modelMapper.map(saved, BookingResponse.class);

        response.setTickets(
                saved.getTicket().stream()
                        .map(this::convertToTicketDto)
                        .toList()
        );

        return response;
    }



    //  Get Booking By PNR
    @Override
    public BookingResponse getBookingByPnr(String pnr) {
        Booking findBookindsByPnr=bookingRepository.findByPnr(pnr);
        if(findBookindsByPnr == null){
            throw  new RuntimeException("Booking Not Found");
        }
        BookingResponse response= modelMapper.map(findBookindsByPnr, BookingResponse.class);
        List<TicketDto> tickets=findBookindsByPnr.getTicket()
                .stream()
                .map(this::convertToTicketDto)
                .toList();
        response.setTickets(tickets);

        return  response;

    }

    @Override
    public String cancelBooking(String pnr) {
        Booking booking = bookingRepository.findByPnr(pnr);
        if(booking == null){
            throw  new RuntimeException("There is no Booking with this pnr");
        }
        if (booking.getStatus().equals("CANCELLED")) {
            return "Booking already cancelled";
        }
        Flight flight = booking.getTicket().get(0).getFlight();
        int ticketCount = booking.getTicket().size();
        // refund seats
        flight.setAvailableSeats(flight.getAvailableSeats() + ticketCount);
        flightRepository.save(flight);
        // Change booking status
        booking.setStatus("CANCELLED");

        bookingRepository.save(booking);
        return "Booking cancelled successfully";
    }

    @Override
    public List<BookingDto> getBookingHistory(Long userId) {
        List<Booking> bookings = bookingRepository.findByUser_UserId(userId);

        return bookings.stream()
                .map(b -> modelMapper.map(b, BookingDto.class))
                .toList();

    }

    @Override
    public BookingDto getBookingById(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    public String payForBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("PAID AND CONFIRMED");
        bookingRepository.save(booking);
        return "Payment successful for booking ID: " + bookingId;
    }

    private TicketDto convertToTicketDto(Ticket ticket) {

        TicketDto dto = new TicketDto();

        dto.setTicketId(ticket.getId());
        dto.setPassengerName(ticket.getPassengerName());
        dto.setSeatNum(ticket.getSeatNum());
        dto.setAge(ticket.getAge());
        dto.setGender(ticket.getGender());
        dto.setPrice(ticket.getPrice());
        dto.setMealPreference(ticket.getMealPreference());

        // --- Flight details ---
        if (ticket.getFlight() != null) {
            dto.setFlightNumber(ticket.getFlight().getFlightNumber());
            dto.setOrigin(ticket.getFlight().getOrigin());
            dto.setDestination(ticket.getFlight().getDestination());
            dto.setDepartureTime(ticket.getFlight().getDepartureTime());
            dto.setArrivalTime(ticket.getFlight().getArrivalTime());
        }

        // --- Booking PNR ---
        if (ticket.getBooking() != null) {
            dto.setPnr(ticket.getBooking().getPnr());
        }

        return dto;
    }


}
