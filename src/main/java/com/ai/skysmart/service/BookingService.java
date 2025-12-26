package com.ai.skysmart.service;

import com.ai.skysmart.dto.BookingDto;
import com.ai.skysmart.dto.BookingRequest;
import com.ai.skysmart.dto.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse create(Long userId, BookingRequest request);
    BookingResponse getBookingByPnr(String pnr);
    String cancelBooking(String pnr);
    //BookingDto createBooking(Long userId, CreateBookingRequest request);
    List<BookingDto> getBookingHistory(Long userId);
    BookingDto getBookingById(String bookingId);

    String payForBooking(String bookingId);

//    create booking
//    generate PNR
//    multiple tickets inside booking
//    booking history
//    find booking by PNR
//    cancel booking
//    auto fill flight details
//    seat deduction logic
//    uses existing entities Flight, Booking, Ticket, User

}
