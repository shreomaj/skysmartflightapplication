package com.ai.skysmart.repository;

import com.ai.skysmart.entity.Flight;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    // return all flights matching origin & destination
    List<Flight> findByOriginAndDestination(String origin, String destination);
   // List<Flight> findByOriginAndDestinationAndJourneyDate(String origin, String destination, LocalDate journeyDate);
   Page<Flight> findByOriginAndDestinationAndJourneyDate(
           String origin,
           String destination,
           LocalDate journeyDate,
           Pageable pageable);

    List<Flight> findByFlightNumber(String number);
    void deleteByFlightNumber(String number);
    Flight findByFlightNumberAndDepartureTime(String flightNumber, LocalDateTime departureTime);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT f FROM Flight f WHERE f.flightId = :id")
    Flight lockFlightForBooking(@Param("id") Long id);


    @Query("SELECT COUNT(f) FROM Flight f")
    long countFlights();

    List<Flight> findByAvailableSeatsLessThan(int seats);

    @Query("""
       SELECT f FROM Flight f 
       WHERE ((f.totalSeats - f.availableSeats) * 100.0 / f.totalSeats) >= :percentage
       """)
    List<Flight> highOccupancyFlights(@Param("percentage") double percentage);




}
