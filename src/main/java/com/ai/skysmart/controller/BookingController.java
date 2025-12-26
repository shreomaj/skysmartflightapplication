package com.ai.skysmart.controller;

import com.ai.skysmart.dto.*;
import com.ai.skysmart.service.BookingService;
import com.ai.skysmart.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Validated
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/{userId}/create")
    public ResponseEntity<BookingResponse> createBooking(@PathVariable Long userId,
                                                         @Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.create(userId, request));
    }

    //    @GetMapping("/all")
//    public ResponseEntity<BookingDto> allBooking(){
//        List<BookingDto> findAll=bookingService.getAll();
//        return new ResponseEntity<>(findAll, HttpStatus.OK);
//    }
    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable String pnr) {
        BookingResponse response = bookingService.getBookingByPnr(pnr);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/cancel/{pnr}")
    public ResponseEntity<String> cancelBooking(@PathVariable String pnr) {
        String response = bookingService.cancelBooking(pnr);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}/history")                   //No correct flight num not saved
    public ResponseEntity<List<BookingDto>> history(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingHistory(userId));
    }

    @GetMapping("/id/{bookingId}")
    public ResponseEntity<BookingDto> getById(@PathVariable String bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @PostMapping("/bookings/{bookingId}/pay")
    public ResponseEntity<String> payForBooking(@PathVariable String bookingId, @Valid @RequestBody PaymentDto paymentDto) {
        String paymentStatus = bookingService.payForBooking(bookingId);
        return ResponseEntity.ok(paymentStatus);
    }



}

