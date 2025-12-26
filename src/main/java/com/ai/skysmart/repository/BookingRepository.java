package com.ai.skysmart.repository;

import com.ai.skysmart.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,String> {
    Booking findByPnr(String pnr);

    List<Booking> findByUser_UserId(Long userId);

    @Query("SELECT COUNT(b) FROM Booking b")
    long countBookings();

    @Query("SELECT SUM(b.totalAmount) FROM Booking b")
    Double totalRevenue();

    @Query("SELECT AVG(b.totalAmount) FROM Booking b")
    Double avgRevenue();

    long countByStatus(String status);

    @Query("""
       SELECT f.origin, f.destination, COUNT(b)
       FROM Booking b 
       JOIN b.ticket t
       JOIN t.flight f
       GROUP BY f.origin, f.destination
       ORDER BY COUNT(b) DESC
       """)
    List<Object[]> findTopRoutes();

}
