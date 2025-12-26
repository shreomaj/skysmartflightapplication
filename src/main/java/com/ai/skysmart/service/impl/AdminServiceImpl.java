package com.ai.skysmart.service.impl;

import com.ai.skysmart.dto.AdminDashboardResponse;
import com.ai.skysmart.dto.FlightDto;
import com.ai.skysmart.dto.RouteStat;
import com.ai.skysmart.entity.Flight;
import com.ai.skysmart.repository.BookingRepository;
import com.ai.skysmart.repository.FlightRepository;
import com.ai.skysmart.service.AdminService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AdminDashboardResponse getDashboard() {

        AdminDashboardResponse response = new AdminDashboardResponse();

        // BASIC STATS
        response.setTotalFlights(flightRepository.count());
        response.setTotalBookings(bookingRepository.count());
        response.setTotalRevenue(bookingRepository.totalRevenue());
        response.setAvgTicketPrice(bookingRepository.avgRevenue());
        response.setCancelledBookings(bookingRepository.countByStatus("CANCELLED"));

        // TOP ROUTES
        List<Object[]> topRoutes = bookingRepository.findTopRoutes();
        response.setTopRoutes(mapRouteStats(topRoutes));

        // LOW AVAILABILITY (<10 seats)
        List<FlightDto> lowAvailability = flightRepository.findByAvailableSeatsLessThan(10)
                .stream().map(this::toDto).collect(Collectors.toList());
        response.setLowAvailabilityFlights(lowAvailability);

        // HIGH OCCUPANCY (>90%)
        List<FlightDto> highDemand = flightRepository.highOccupancyFlights(90)
                .stream().map(this::toDto).collect(Collectors.toList());
        response.setHighDemandFlights(highDemand);

        return response;
    }

    // Convert Flight → FlightDto
    private FlightDto toDto(Flight flight) {
        return modelMapper.map(flight, FlightDto.class);
    }

    // Convert Object[] → RouteStat
    private List<RouteStat> mapRouteStats(List<Object[]> raw) {
        return raw.stream().map(row ->
                new RouteStat(
                        row[0].toString(),  // origin
                        row[1].toString(),  // destination
                        ((Number) row[2]).longValue() // bookings
                )
        ).collect(Collectors.toList());
    }
}
