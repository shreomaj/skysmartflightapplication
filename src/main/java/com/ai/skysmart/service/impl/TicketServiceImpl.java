package com.ai.skysmart.service.impl;
import com.ai.skysmart.dto.TicketDto;
import com.ai.skysmart.entity.Booking;
import com.ai.skysmart.entity.Flight;
import com.ai.skysmart.entity.Ticket;
import com.ai.skysmart.repository.BookingRepository;
import com.ai.skysmart.repository.FlightRepository;
import com.ai.skysmart.repository.TicketRepository;
import com.ai.skysmart.service.TicketService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private FlightRepository flightRepo;

    @Autowired
    private ModelMapper modelMapper;


    // 1️⃣ Get all tickets by PNR
    @Override
    public List<TicketDto> getTicketsByPnr(String pnr) {
        List<Ticket> tickets = ticketRepository.findByBooking_Pnr(pnr);

        if (tickets.isEmpty()) {
            throw new RuntimeException("No tickets found for PNR: " + pnr);
        }

        return tickets.stream()
                .map(this::mapTicketToDto)
                .toList();
    }


    // 2️⃣ Get a single ticket
    @Override
    public TicketDto getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return mapTicketToDto(ticket);
    }


    // 3️⃣ Change seat number (safe)
    @Override
    public TicketDto changeSeat(Long ticketId, Integer newSeat) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Flight flight = ticket.getFlight();

        // Validate seat range
        if (newSeat < 1 || newSeat > flight.getTotalSeats()) {
            throw new RuntimeException("Seat number out of range");
        }

        // Check if seat is already taken
        boolean taken = ticketRepository.existsByFlightAndSeatNum(flight, newSeat);
        if (taken) {
            throw new RuntimeException("Seat already taken");
        }

        ticket.setSeatNum(newSeat);
        ticketRepository.save(ticket);

        return mapTicketToDto(ticket);
    }

    @Override
    public void canceTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Booking booking = ticket.getBooking();
        Flight flight = ticket.getFlight();

        // Remove ticket from booking
        booking.getTicket().removeIf(t -> t.getId().equals(ticketId));

        // Increase available seats
        flight.setAvailableSeats(flight.getAvailableSeats() + 1);
        flightRepo.save(flight);

        // Delete ticket
        ticketRepository.delete(ticket);

        // Recalculate total booking cost
        double newTotal = booking.getTicket().stream()
                .mapToDouble(Ticket::getPrice)
                .sum();

        booking.setTotalAmount(newTotal);

        // If no tickets left → cancel entire booking
        if (booking.getTicket().isEmpty()) {
            booking.setStatus("CANCELLED");
        }

        bookingRepo.save(booking);
    }


    // 5️⃣ Generate boarding pass (PDF)
    @Override
    public byte[] generateBoardingPass(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        try (PDDocument doc = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(doc, page);

            cs.setFont(PDType1Font.HELVETICA_BOLD, 20);
            cs.beginText();
            cs.newLineAtOffset(50, 720);
            cs.showText("BOARDING PASS");
            cs.endText();

            cs.setFont(PDType1Font.HELVETICA, 14);

            int y = 690;

            cs.beginText(); cs.newLineAtOffset(50, y);
            cs.showText("Passenger: " + ticket.getPassengerName());
            cs.endText(); y -= 20;

            cs.beginText(); cs.newLineAtOffset(50, y);
            cs.showText("PNR: " + ticket.getBooking().getPnr());
            cs.endText(); y -= 20;

            cs.beginText(); cs.newLineAtOffset(50, y);
            cs.showText("Flight: " + ticket.getFlight().getFlightNumber());
            cs.endText(); y -= 20;

            cs.beginText(); cs.newLineAtOffset(50, y);
            cs.showText("Route: " + ticket.getFlight().getOrigin() + " → " +
                    ticket.getFlight().getDestination());
            cs.endText(); y -= 20;

            cs.beginText(); cs.newLineAtOffset(50, y);
            cs.showText("Seat: " + ticket.getSeatNum());
            cs.endText();

            cs.close();

            doc.save(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating boarding pass PDF");
        }
    }

    @Override
    public void generateTicketsForBooking(Booking booking) {

    }


    // 6️⃣ Generate full booking PDF (all passengers)
    @Override
    public byte[] generateBookingPdf(String bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        try (PDDocument doc = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(doc, page);

            cs.setFont(PDType1Font.HELVETICA_BOLD, 18);
            cs.beginText();
            cs.newLineAtOffset(50, 740);
            cs.showText("FLIGHT BOOKING TICKET");
            cs.endText();

            cs.setFont(PDType1Font.HELVETICA, 14);

            int y = 710;

            cs.beginText(); cs.newLineAtOffset(50, y);
            cs.showText("PNR: " + booking.getPnr());
            cs.endText(); y -= 20;

            cs.beginText(); cs.newLineAtOffset(50, y);
            cs.showText("Status: " + booking.getStatus());
            cs.endText(); y -= 20;

            cs.beginText(); cs.newLineAtOffset(50, y);
            cs.showText("Total Amount: ₹" + booking.getTotalAmount());
            cs.endText(); y -= 40;

            cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
            cs.beginText(); cs.newLineAtOffset(50, y);
            cs.showText("Passengers:");
            cs.endText();
            y -= 25;

            cs.setFont(PDType1Font.HELVETICA, 14);

            for (Ticket t : booking.getTicket()) {

                cs.beginText();
                cs.newLineAtOffset(50, y);
                cs.showText("• " + t.getPassengerName() +
                        " | Seat: " + t.getSeatNum() +
                        " | Age: " + t.getAge() +
                        " | Gender: " + t.getGender());
                cs.endText();
                y -= 20;
            }

            cs.close();
            doc.save(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating booking PDF");
        }
    }



    // Convert Ticket → DTO
    private TicketDto mapTicketToDto(Ticket t) {
        return new TicketDto(
                t.getId(),
                t.getPassengerName(),
                t.getSeatNum(),
                t.getAge(),
                t.getGender(),
                t.getPrice(),
                t.getMealPreference(),
                t.getFlight().getFlightNumber(),
                t.getFlight().getOrigin(),
                t.getFlight().getDestination(),
                t.getFlight().getDepartureTime(),
                t.getFlight().getArrivalTime(),
                t.getBooking().getPnr()
        );
    }
}
